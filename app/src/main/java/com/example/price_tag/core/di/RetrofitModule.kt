package com.example.price_tag.core.di

import com.example.price_tag.core.common.AppConstants
import com.example.price_tag.core.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    //    @Provides
    //    @Singleton
    //    fun provideMoshi(): Moshi {
    //        return Moshi.Builder()
    //            .add(KotlinJsonAdapterFactory())
    //            .build()
    //    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL)
            .client(okHttpClient) //            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
