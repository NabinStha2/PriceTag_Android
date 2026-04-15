package com.example.price_tag.di

import com.example.price_tag.domain.repository.auth.AuthRepository
import com.example.price_tag.domain.repository.category.CategoryRepository
import com.example.price_tag.domain.repository.product.ProductRepository
import com.example.price_tag.domain.usecase.auth.LoginUseCase
import com.example.price_tag.domain.usecase.auth.RegisterUseCase
import com.example.price_tag.domain.usecase.auth.VerifyOtpUseCase
import com.example.price_tag.domain.usecase.category.GetCategoriesUseCase
import com.example.price_tag.domain.usecase.category.GetSelectedCategoryUseCase
import com.example.price_tag.domain.usecase.category.GetSubCategoriesUseCase
import com.example.price_tag.domain.usecase.category.SetSelectedCategoryUseCase
import com.example.price_tag.domain.usecase.product.GetAllSubCategoryProductsUseCase
import com.example.price_tag.domain.usecase.product.GetAllSubCategorySearchProductsUseCase
import com.example.price_tag.domain.usecase.product.GetProductDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVerifyOtpUseCase(repository: AuthRepository): VerifyOtpUseCase {
        return VerifyOtpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSubCategoriesUseCase(repository: CategoryRepository): GetSubCategoriesUseCase {
        return GetSubCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSelectedCategoryUseCase(repository: CategoryRepository): GetSelectedCategoryUseCase {
        return GetSelectedCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetSelectedCategoryUseCase(repository: CategoryRepository): SetSelectedCategoryUseCase {
        return SetSelectedCategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllSubCategoryProductsUseCase(repository: ProductRepository): GetAllSubCategoryProductsUseCase {
        return GetAllSubCategoryProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllSubCategorySearchProductsUseCase(repository: ProductRepository): GetAllSubCategorySearchProductsUseCase {
        return GetAllSubCategorySearchProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductDetailsUseCase(repository: ProductRepository): GetProductDetailsUseCase {
        return GetProductDetailsUseCase(repository)
    }
}
