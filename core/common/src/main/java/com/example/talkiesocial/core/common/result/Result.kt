package com.example.talkiesocial.core.common.result

sealed interface AppResult<out D, out E: BaseError> {
    data class Success<out D>(val data: D) : AppResult<D, Nothing>
    data class Error<out E: BaseError>(val error: E) : AppResult<Nothing, E>
}
