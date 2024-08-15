package com.example.price_tag.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class RetryInterceptor(private val maxRetry: Int = 3) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response
        var attempt = 0
        val request = chain.request()

        while (true) {
            try {
                response = chain.proceed(request)
                // Break the loop if the response is successful or we have reached the max retry count
                if (response.isSuccessful || attempt >= maxRetry) {
                    return response
                }
            } catch (e: IOException) {
                // Handle IOExceptions and retry if necessary
                if (attempt >= maxRetry) {
                    throw e
                }
            }

            attempt++
            // Optionally add a delay between retries
            Thread.sleep(1000L * attempt) // Exponential backoff (1s, 2s, 3s, ...)
        }
    }
}