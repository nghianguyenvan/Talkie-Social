package com.example.talkiesocial.core.network.util

import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.common.result.DataError
import com.example.talkiesocial.core.network.model.NetworkErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Helper function để thực hiện call API an toàn và parse lỗi chung.
 * @param mapBusinessError: Lambda cho phép từng feature tự parse mã lỗi riêng của mình.
 */
suspend fun <T, E : BaseError> safeApiCall(
    execute: suspend () -> Response<T>,
    mapBusinessError: (code: String?, message: String?) -> E? = { _, _ -> null }
): AppResult<T, BaseError> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            AppResult.Success(body)
        } else {
            // Parse error body từ Backend
            val errorJson = response.errorBody()?.string()
            val errorResponse = try {
                errorJson?.let { Json.decodeFromString<NetworkErrorResponse>(it) }
            } catch (e: Exception) {
                null
            }

            // 1. Ưu tiên lỗi Business do feature định nghĩa
            val businessError = mapBusinessError(errorResponse?.code, errorResponse?.message)
            if (businessError != null) return AppResult.Error(businessError)

            // 2. Nếu không có lỗi Business, dùng lỗi Network chung dựa trên HTTP code
            when (response.code()) {
                408 -> AppResult.Error(DataError.Network.Timeout)
                500, 502, 503 -> AppResult.Error(DataError.Network.ServerError)
                else -> AppResult.Error(DataError.Network.Unknown)
            }
        }
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException -> AppResult.Error(DataError.Network.NoInternet)
            is TimeoutException -> AppResult.Error(DataError.Network.Timeout)
            is kotlinx.serialization.SerializationException -> AppResult.Error(DataError.Network.Serialization)
            else -> AppResult.Error(DataError.Network.Unknown)
        }
    }
}
