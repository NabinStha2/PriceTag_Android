package com.example.price_tag.core.di

import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.network.AuthInterceptor
import com.example.price_tag.core.network.AuthErrorInterceptor
import com.example.price_tag.core.network.RetryInterceptor
import com.example.price_tag.core.utils.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(dataStoreManager: DataStoreManager): AuthInterceptor {
        return AuthInterceptor(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideRetryInterceptor(): RetryInterceptor {
        return RetryInterceptor(maxRetry = 3)
    }

    @Provides
    @Singleton
    fun provideAuthErrorInterceptor(dataStoreManager: DataStoreManager): AuthErrorInterceptor {
        return AuthErrorInterceptor(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authErrorInterceptor: AuthErrorInterceptor,
        // retryInterceptor: RetryInterceptor // Optional, currently commented out in AppModule
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(authErrorInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}
