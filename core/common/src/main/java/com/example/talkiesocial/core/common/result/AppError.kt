package com.example.talkiesocial.core.common.result

sealed interface AppError {
    sealed interface Network : AppError {
        object NoInternet : Network
        object Timeout : Network
        object ServerError : Network
        object Unknown : Network
    }
    
    sealed interface Auth : AppError {
        object InvalidCredentials : Auth
        object UserAlreadyExists : Auth
        object WeakPassword : Auth
        object AccountLocked : Auth
    }
}
