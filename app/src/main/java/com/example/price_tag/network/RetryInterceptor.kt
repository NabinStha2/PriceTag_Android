package com.example.price_tag.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetry: Int = 3) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        var attempt = 0
        var exception: IOException? = null

        while (attempt < maxRetry) {
            try {
                response = chain.proceed(chain.request())

                // Log the status of each attempt
                println("Attempt ${attempt + 1}: Response code: ${response.code}")

                // If the response is successful, return it
                if (response.isSuccessful) {
                    return response
                }

                // Close the response before retrying (but not the final one)
//                response.close()
            } catch (e: IOException) {
                exception = e
                println("Attempt ${attempt + 1} failed with IOException: ${e.message}")
            }

            attempt++
            // Optionally add a delay between retries
            Thread.sleep(1000L * attempt) // Exponential backoff (1s, 2s, 3s, ...)
        }

        // If we have a response, return it, otherwise throw the last exception
        return if (response != null && response.isSuccessful) {
            response
        } else {
            response ?: throw exception
                ?: IOException("Failed to execute request after $maxRetry retries")
        }
    }
}
