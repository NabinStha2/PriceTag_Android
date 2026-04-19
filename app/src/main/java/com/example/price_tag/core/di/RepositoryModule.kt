package com.example.price_tag.core.di

import com.example.price_tag.data.repository.auth.AuthRepositoryImpl
import com.example.price_tag.data.repository.category.CategoryRepositoryImpl
import com.example.price_tag.data.repository.product.ProductRepositoryImpl
import com.example.price_tag.domain.repository.auth.AuthRepository
import com.example.price_tag.domain.repository.category.CategoryRepository
import com.example.price_tag.domain.repository.product.ProductRepository
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
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
