package com.example.price_tag.core.di

import com.example.price_tag.core.navigation.NavigationCoordinator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    /**
     * NavigationCoordinator is a @Singleton already via @Inject,
     * but we provide it explicitly so the same instance is available
     * both in the Activity (for NavController wiring) and in any
     * ViewModel across the app.
     */
    @Provides
    @Singleton
    fun provideNavigationCoordinator(): NavigationCoordinator =
        NavigationCoordinator()
}