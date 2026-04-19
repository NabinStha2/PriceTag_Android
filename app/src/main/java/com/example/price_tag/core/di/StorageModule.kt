package com.example.price_tag.core.di

import android.content.Context
import android.content.SharedPreferences
import com.example.price_tag.core.common.AppConstants
import com.example.price_tag.core.utils.SharedPreferencesUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesUtils(sharedPreferences: SharedPreferences): SharedPreferencesUtils {
        return SharedPreferencesUtils(sharedPreferences)
    }
}
