package com.example.price_tag.core.network

import android.util.Log
import com.example.price_tag.core.datastore.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthErrorInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Check if the response is 401 Unauthorized
        if (response.code == 401) {
            Log.w("AuthErrorInterceptor", "Received 401 Unauthorized. Clearing authentication tokens.")
            runBlocking { dataStoreManager.clearAuthTokens() }
        }

        return response
    }
}
