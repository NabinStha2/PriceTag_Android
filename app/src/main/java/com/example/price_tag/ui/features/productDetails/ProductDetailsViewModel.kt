package com.example.price_tag.ui.features.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.domain.model.product.ProductVariants
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.price_tag.domain.usecase.product.GetProductDetailsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val getProductDetailsUseCase: GetProductDetailsUseCase) :
    ViewModel() {

    private val _getSingleProductDetailsData =
        MutableStateFlow<ResourceState<SingleProductDetails>>(ResourceState.Initial)
    val getSingleProductDetailsData: MutableStateFlow<ResourceState<SingleProductDetails>> =
        _getSingleProductDetailsData

    // State for color, size, and variant selection
    private val _selectedColor = MutableStateFlow<String?>(null)
    val selectedColor: StateFlow<String?> = _selectedColor.asStateFlow()

    private val _selectedSize = MutableStateFlow<Int?>(null)
    val selectedSize: StateFlow<Int?> = _selectedSize.asStateFlow()

    private val _selectedVariant = MutableStateFlow<ProductVariants?>(null)
    val selectedVariant: StateFlow<ProductVariants?> = _selectedVariant.asStateFlow()

    fun getSingleProductDetails(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductDetailsUseCase(productId).collectLatest { response ->
                _getSingleProductDetailsData.value = response

                // Initialize selections when product details are fetched successfully
                if (response is ResourceState.Success) {
                    val productDetails = response.data
                    val colorSizeVariantGroup = productDetails.colorSizeVariantGroup ?: emptyList()

                    // Set default color (first available)
                    val defaultColor = colorSizeVariantGroup.firstOrNull()?.colorHex
                    _selectedColor.value = defaultColor

                    // Set default size (first available for selected color)
                    val defaultSize = colorSizeVariantGroup.firstOrNull {
                        it.colorHex == defaultColor
                    }?.variants?.firstOrNull()?.size?.id?.toInt()
                    _selectedSize.value = defaultSize

                    // Set default variant
                    val defaultVariant = colorSizeVariantGroup.firstOrNull {
                        it.colorHex == defaultColor
                    }?.variants?.firstOrNull {
                        it.size?.id?.toInt() == defaultSize
                    }
                    _selectedVariant.value = defaultVariant
                }
            }
        }
    }

    fun onColorSelected(colorHex: String) {
        _selectedColor.value = colorHex
        // Reset size and variant when color changes
        _selectedSize.value = null
        _selectedVariant.value = null
    }

    fun onSizeSelected(sizeId: Int, productVariants: ProductVariants?, colorSizeVariantGroup: List<*>) {
        _selectedSize.value = sizeId
        _selectedVariant.value = productVariants
    }
}
