package com.example.price_tag.core.di

import com.example.price_tag.feature.auth.data.remote.AuthDataSource
import com.example.price_tag.feature.auth.data.remote.AuthDataSourceImpl
import com.example.price_tag.feature.category.data.remote.CategoryDataSource
import com.example.price_tag.feature.category.data.remote.CategoryDataSourceImpl
import com.example.price_tag.feature.product.data.remote.ProductDataSource
import com.example.price_tag.feature.product.data.remote.ProductDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindCategoryDataSource(
        categoryDataSourceImpl: CategoryDataSourceImpl
    ): CategoryDataSource

    @Binds
    @Singleton
    abstract fun bindProductDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource
}
