package com.example.price_tag.core.network

import android.util.Log
import com.example.price_tag.core.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { dataStoreManager.bearerToken.first() }

        Log.i("Authorization", "Bearer $token")

        // List of endpoints that require authentication
        val endpointsThatDoesNotNeedAuth =
            listOf("/auth/login", "/auth/register", "/auth/verify-otp")

        // Check if the request URL path matches one of the protected endpoints
        val shouldNotAddAuth = endpointsThatDoesNotNeedAuth.any { endpoint ->
            originalRequest.url.encodedPath.contains(endpoint)
        }

        val newRequest = originalRequest.newBuilder().apply {
            addHeader("Content-Type", "application/json")
            addHeader("accept", "application/json")
            if (!shouldNotAddAuth) {
                if (token != null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
        }.build()
        return chain.proceed(newRequest)
    }
}
