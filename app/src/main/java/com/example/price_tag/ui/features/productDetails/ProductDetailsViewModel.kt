package com.example.price_tag.ui.features.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.product_details.ProductDetailsResponse
import com.example.price_tag.domain.usecase.product.GetProductDetailsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _getSingleProductDetailsData =
        MutableStateFlow<ResourceState<ProductDetailsResponse>>(ResourceState.Initial)
    val getSingleProductDetailsData: MutableStateFlow<ResourceState<ProductDetailsResponse>> =
        _getSingleProductDetailsData


    fun getSingleProductDetails(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductDetailsUseCase(productId)
                .collectLatest { response ->
                    _getSingleProductDetailsData.value = response
                }
        }
    }
}
