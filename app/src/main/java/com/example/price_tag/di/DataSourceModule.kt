package com.example.price_tag.di

import com.example.price_tag.data.datasource.auth.AuthDataSource
import com.example.price_tag.data.datasource.auth.AuthDataSourceImpl
import com.example.price_tag.data.datasource.category.CategoryDataSource
import com.example.price_tag.data.datasource.category.CategoryDataSourceImpl
import com.example.price_tag.data.datasource.product.ProductDataSource
import com.example.price_tag.data.datasource.product.ProductDataSourceImpl
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
