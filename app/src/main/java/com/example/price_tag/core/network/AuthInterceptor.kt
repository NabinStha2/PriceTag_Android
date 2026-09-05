package com.example.price_tag.core.network

import com.example.price_tag.core.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.isPublicEndpoint()) {
            return chain.proceed(originalRequest)
        }

        // OkHttp interceptors are synchronous, so the DataStore Flow must be read here.
        val token = runBlocking { dataStoreManager.bearerToken.first() }
        val authenticatedRequest = originalRequest.newBuilder()
            .apply {
                if (!token.isNullOrBlank()) {
                    header(
                        AUTHORIZATION_HEADER,
                        "$BEARER_PREFIX$token",
                    )
                }
            }
            .build()

        return chain.proceed(authenticatedRequest)
    }

    private fun Request.isPublicEndpoint(): Boolean {
        val requestPath = url.encodedPath.trimEnd('/')
        return PUBLIC_ENDPOINTS.any { requestPath.endsWith(it) }
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "

        val PUBLIC_ENDPOINTS = setOf(
            "/auth/login",
            "/auth/register",
            "/auth/verify-otp",
        )
    }
}
