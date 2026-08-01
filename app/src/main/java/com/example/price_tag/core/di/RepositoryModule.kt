package com.example.price_tag.core.di

import com.example.price_tag.core.session.data.repository.SessionRepositoryImpl
import com.example.price_tag.core.session.domain.repository.SessionRepository
import com.example.price_tag.feature.auth.data.repository.AuthRepositoryImpl
import com.example.price_tag.feature.auth.domain.repository.AuthRepository
import com.example.price_tag.feature.cart.data.repository.CartRepositoryImpl
import com.example.price_tag.feature.cart.domain.repository.CartRepository
import com.example.price_tag.feature.category.data.repository.CategoryRepositoryImpl
import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import com.example.price_tag.feature.product.data.repository.ProductRepositoryImpl
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(sessionRepositoryImpl: SessionRepositoryImpl): SessionRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository
}
