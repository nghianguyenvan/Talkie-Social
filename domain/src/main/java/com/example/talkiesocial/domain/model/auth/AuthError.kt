package com.example.talkiesocial.domain.model.auth

import com.example.talkiesocial.core.common.result.BaseError

sealed interface AuthError : BaseError {
    object InvalidEmail : AuthError
    object InvalidPassword : AuthError
    object UserNotFound : AuthError
    object WrongPassword : AuthError
    object UserAlreadyExists : AuthError
    object Unknown : AuthError
}
