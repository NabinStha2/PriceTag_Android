package com.example.price_tag.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.price_tag.data.AppConstants
import com.example.price_tag.data.api.ApiService
import com.example.price_tag.data.datasource.auth.AuthDataSource
import com.example.price_tag.data.datasource.auth.AuthDataSourceImpl
import com.example.price_tag.data.datasource.category.CategoryDataSource
import com.example.price_tag.data.datasource.category.CategoryDataSourceImpl
import com.example.price_tag.data.repository.auth.AuthRepository
import com.example.price_tag.data.repository.category.CategoryRepository
import com.example.price_tag.network.AuthInterceptor
import com.example.price_tag.network.RetryInterceptor
import com.example.price_tag.utils.SharedPreferencesUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesSharedPreferencesUtils(sharedPreferences: SharedPreferences): SharedPreferencesUtils {
        return SharedPreferencesUtils(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesAuthInterceptor(sharedPreferencesUtils: SharedPreferencesUtils): AuthInterceptor {
        return AuthInterceptor(sharedPreferencesUtils)
    }

    @Provides
    @Singleton
    fun providesRetryInterceptor(): RetryInterceptor {
        return RetryInterceptor(maxRetry = 3)
    }


    @Provides
    @Singleton
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor,
        retryInterceptor: RetryInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(retryInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder().baseUrl(AppConstants.APP_BASE_URL).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }


    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun providesAuthDataSource(apiService: ApiService): AuthDataSource {
        return AuthDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepository(authDataSource)
    }


    @Provides
    @Singleton
    fun providesCategoryDataSource(apiService: ApiService): CategoryDataSource {
        return CategoryDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesCategoryRepository(categoryDataSource: CategoryDataSource): CategoryRepository {
        return CategoryRepository(categoryDataSource)
    }


}