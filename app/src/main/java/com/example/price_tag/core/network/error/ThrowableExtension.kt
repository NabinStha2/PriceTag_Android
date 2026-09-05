package com.example.price_tag.core.network.error

import kotlinx.serialization.SerializationException
import java.io.IOException
import java.util.concurrent.CancellationException

fun Throwable.toUserMessage(): String {
    return when (this) {
        is CancellationException -> throw this
        is IOException -> "Check your internet connection"
        is SerializationException -> "Unexpected server response"
        else -> localizedMessage ?: "Something went wrong"
    }
}
