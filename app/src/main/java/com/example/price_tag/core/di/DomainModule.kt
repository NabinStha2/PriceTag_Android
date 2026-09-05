package com.example.price_tag.core.di

import com.example.price_tag.feature.auth.domain.repository.AuthRepository
import com.example.price_tag.feature.auth.domain.usecase.LoginUseCase
import com.example.price_tag.feature.auth.domain.usecase.RegisterUseCase
import com.example.price_tag.feature.auth.domain.usecase.VerifyOtpUseCase
import com.example.price_tag.feature.cart.domain.repository.CartRepository
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.price_tag.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.example.price_tag.feature.cart.domain.usecase.UpdateCartItemQuantityUseCase
import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import com.example.price_tag.feature.category.domain.usecase.GetCategoriesUseCase
import com.example.price_tag.feature.category.domain.usecase.GetSelectedCategoryUseCase
import com.example.price_tag.feature.category.domain.usecase.GetSubCategoriesWithSelectedCategoryUseCase
import com.example.price_tag.feature.category.domain.usecase.SetSelectedCategoryUseCase
import com.example.price_tag.feature.checkout.domain.repository.CheckoutRepository
import com.example.price_tag.feature.checkout.domain.usecase.CreateAddressUseCase
import com.example.price_tag.feature.checkout.domain.usecase.CreateOrderUseCase
import com.example.price_tag.feature.checkout.domain.usecase.GetAddressesUseCase
import com.example.price_tag.feature.checkout.domain.usecase.InitializePaymentUseCase
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.price_tag.feature.product.domain.usecase.GetAllSubCategoryProductsUseCase
import com.example.price_tag.feature.product.domain.usecase.GetAllSubCategorySearchProductsUseCase
import com.example.price_tag.feature.product.domain.usecase.GetProductDetailsRatingReviewsUseCase
import com.example.price_tag.feature.product.domain.usecase.GetProductDetailsUseCase
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
    fun provideGetSubCategoriesWithSelectedCategoryUseCase(repository: CategoryRepository): GetSubCategoriesWithSelectedCategoryUseCase {
        return GetSubCategoriesWithSelectedCategoryUseCase(repository)
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

    @Provides
    @Singleton
    fun provideGetProductDetailsRatingReviewsUseCase(repository: ProductRepository): GetProductDetailsRatingReviewsUseCase {
        return GetProductDetailsRatingReviewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCartItemsUseCase(repository: CartRepository): GetAllCartItemsUseCase {
        return GetAllCartItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveCartItemUseCase(repository: CartRepository): RemoveCartItemUseCase {
        return RemoveCartItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateCartItemQuantityUseCase(repository: CartRepository): UpdateCartItemQuantityUseCase {
        return UpdateCartItemQuantityUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideCreateAddressUseCase(repository: CheckoutRepository): CreateAddressUseCase {
        return CreateAddressUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAddressesUseCase(repository: CheckoutRepository): GetAddressesUseCase {
        return GetAddressesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateOrderUseCase(repository: CheckoutRepository): CreateOrderUseCase {
        return CreateOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInitializePaymentUseCase(repository: CheckoutRepository): InitializePaymentUseCase {
        return InitializePaymentUseCase(repository)
    }
}
