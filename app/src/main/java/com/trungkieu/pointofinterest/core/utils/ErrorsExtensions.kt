package com.trungkieu.pointofinterest.core.utils

import com.trungkieu.pointofinterest.R

sealed class ErrorDisplayObject(val errorMessage: Int) {
    object GenericError : ErrorDisplayObject(R.string.message_ui_state_error)
}

fun Throwable.toDisplayObject(): ErrorDisplayObject {
    return ErrorDisplayObject.GenericError
}