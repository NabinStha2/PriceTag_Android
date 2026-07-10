package com.example.price_tag.ui.features.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.domain.model.product.ProductVariants
import com.example.price_tag.domain.usecase.product.GetProductDetailsRatingReviewsUseCase
import com.example.price_tag.domain.usecase.product.GetProductDetailsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val productDetailsRatingReviewsUseCase: GetProductDetailsRatingReviewsUseCase,
) : ViewModel() {
    private val _productDetailsState: MutableStateFlow<ProductDetailsContract.ProductDetailsState> =
        MutableStateFlow(ProductDetailsContract.ProductDetailsState())
    val productDetailsState: StateFlow<ProductDetailsContract.ProductDetailsState> =
        _productDetailsState.asStateFlow()

    private val _effect = MutableSharedFlow<ProductDetailsContract.ProductDetailsEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(productDetailsIntent: ProductDetailsContract.ProductDetailsIntent) {
        when (productDetailsIntent) {
            is ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetails -> {
                getSingleProductDetails(productDetailsIntent.productId)
            }

            is ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetailsRatingReviews -> {
                getSingleProductDetailsRatingReviews(productDetailsIntent.productId)
            }

            is ProductDetailsContract.ProductDetailsIntent.NavigationToRatingReview -> {
                navigationToRatingReview(productDetailsIntent.productId)
            }
        }
    }

    private fun getSingleProductDetails(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductDetailsUseCase(productId).collectLatest { response ->
                _productDetailsState.update { state ->
                    when (response) {
                        is ResourceState.Success -> {
                            val productDetails = response.data
                            val colorSizeVariantGroup =
                                productDetails.colorSizeVariantGroup ?: emptyList()

                            // Set default color (first available)
                            val defaultColor = colorSizeVariantGroup.firstOrNull()?.colorHex

                            // Set default size (first available for selected color)
                            val defaultSize = colorSizeVariantGroup.firstOrNull {
                                it.colorHex == defaultColor
                            }?.variants?.firstOrNull()?.size?.id?.toInt()

                            // Set default variant
                            val defaultVariant = colorSizeVariantGroup.firstOrNull {
                                it.colorHex == defaultColor
                            }?.variants?.firstOrNull {
                                it.size?.id?.toInt() == defaultSize
                            }

                            state.copy(
                                getSingleProductDetailsData = response,
                                selectedColor = defaultColor,
                                selectedSize = defaultSize,
                                selectedVariant = defaultVariant,
                            )
                        }

                        else -> {
                            state.copy(
                                getSingleProductDetailsData = response,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getSingleProductDetailsRatingReviews(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            productDetailsRatingReviewsUseCase(productId = productId).collectLatest { response ->
                _productDetailsState.update { state ->
                    when (response) {
                        is ResourceState.Success -> {
                            state.copy(
                                getSingleProductDetailsRatingReviews = response,
                            )
                        }

                        else -> {
                            state.copy(
                                getSingleProductDetailsRatingReviews = response,
                            )
                        }
                    }
                }

            }
        }

    }

    private fun navigationToRatingReview(productId: Long) {
        viewModelScope.launch {
            _effect.emit(ProductDetailsContract.ProductDetailsEffect.NavigateToRatingReviewView(productId))
        }
    }

    fun onColorSelected(colorHex: String) {
        _productDetailsState.update {
            it.copy(
                selectedColor = colorHex,
                selectedSize = null,
                selectedVariant = null,
            )
        }
    }

    fun onSizeSelected(
        sizeId: Int,
        productVariants: ProductVariants?,
        colorSizeVariantGroup: List<*>,
    ) {
        _productDetailsState.update {
            it.copy(
                selectedSize = sizeId,
                selectedVariant = productVariants,
            )
        }
    }
}
