package com.smile.domain

import java.io.IOException

sealed class ErrorClass(msg: String = "") : Exception(msg) {
    object GeneralError : ErrorClass()
    object NoInternet : ErrorClass()
}

fun Throwable.parseToErrorClass(): ErrorClass = when (this) {
    is IOException -> ErrorClass.NoInternet
    else -> ErrorClass.GeneralError
}
