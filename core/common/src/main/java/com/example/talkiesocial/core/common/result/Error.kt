package com.example.talkiesocial.core.common.result

/**
 * Interface gốc cho tất cả các loại lỗi trong hệ thống.
 */
interface BaseError

/**
 * Các lỗi mang tính hệ thống/toàn cục.
 */
sealed interface DataError : BaseError {
    sealed interface Network : DataError {
        object NoInternet : Network
        object Timeout : Network
        object ServerError : Network
        object Serialization : Network
        object Unknown : Network
    }
    
    sealed interface Local : DataError {
        object DiskFull : Local
    }
}
