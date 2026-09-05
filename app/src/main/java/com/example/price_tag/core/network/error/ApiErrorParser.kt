package com.example.price_tag.core.network.error

import com.example.price_tag.core.common.data.model.CommonErrorResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiErrorParser @Inject constructor(
    private val json: Json,
) {
    fun parseMessage(errorBody: String?): String? {
        return errorBody?.let { body ->
            runCatching {
                json.decodeFromString<CommonErrorResponse>(body).message
            }.getOrNull()
        }
    }
}