package com.example.price_tag.core.di

import com.example.price_tag.data.remote.datasource.auth.AuthDataSource
import com.example.price_tag.data.remote.datasource.auth.AuthDataSourceImpl
import com.example.price_tag.data.remote.datasource.category.CategoryDataSource
import com.example.price_tag.data.remote.datasource.category.CategoryDataSourceImpl
import com.example.price_tag.data.remote.datasource.product.ProductDataSource
import com.example.price_tag.data.remote.datasource.product.ProductDataSourceImpl
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
