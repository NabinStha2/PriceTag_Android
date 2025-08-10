package com.example.price_tag.ui.features.product

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.product.ProductResponse
import com.example.price_tag.data.model.product.ProductResult
import com.example.price_tag.data.repository.product.ProductRepository
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    val productsList = mutableStateListOf<ProductResult>()
    val searchProductsList = mutableStateListOf<ProductResult>()
    val isLoadingMore = mutableStateOf(false)
    var isLastPage = mutableStateOf(false)
    var isSearchLastPage = mutableStateOf(false)
    var currentPage = mutableIntStateOf(1)

    private val _getAllSubCategoryProductsData =
        MutableStateFlow<ResourceState<ProductResponse>>(ResourceState.Initial())
    val getAllSubCategoryProductsData: MutableStateFlow<ResourceState<ProductResponse>> =
        _getAllSubCategoryProductsData

    private val _getAllSubCategorySearchProductsData =
        MutableStateFlow<ResourceState<ProductResponse>>(ResourceState.Initial())
    val getAllSubCategorySearchProductsData: MutableStateFlow<ResourceState<ProductResponse>> =
        _getAllSubCategorySearchProductsData


    fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int? = 1,
        limit: Int? = 6,
        sortBy: String? = "createdAt",
        order: String? = "asc"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isLoadingMore.value) return@launch

            isLoadingMore.value = true
            isLastPage.value = false

            productRepository.getAllSubCategoryProducts(subCategoryId, page, limit, sortBy, order)
                .collectLatest { getAllSubCategoryProductsResponse ->
                    when (getAllSubCategoryProductsResponse) {
                        is ResourceState.Success -> {
                            if (page == 1) {
                                productsList.clear()
                            }
                            _getAllSubCategoryProductsData.value =
                                getAllSubCategoryProductsResponse
                            productsList.addAll(getAllSubCategoryProductsResponse.data.data?.results.orEmpty())
                            currentPage.intValue =
                                getAllSubCategoryProductsResponse.data.data?.pagination?.currentPage!!.toInt() + 1
                            isLastPage.value =
                                getAllSubCategoryProductsResponse.data.data?.pagination?.hasNext == false
                        }

                        is ResourceState.Error -> {
                            _getAllSubCategoryProductsData.value =
                                getAllSubCategoryProductsResponse
                        }

                        else -> {}
                    }
                    isLoadingMore.value = false
                }
        }
    }

    fun getAllSubCategorySearchProductsData(
        subCategoryId: Long,
        page: Int? = 1,
        limit: Int? = 6,
        sortBy: String? = "createdAt",
        order: String? = "asc",
        name: String? = "",
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isLoadingMore.value) return@launch

            isLoadingMore.value = true
            isLastPage.value = false

            productRepository.getAllSubCategorySearchProducts(
                subCategoryId,
                page,
                limit,
                sortBy,
                order,
                name
            ).collectLatest { getAllSubCategorySearchProductsResponse ->
                when (getAllSubCategorySearchProductsResponse) {
                    is ResourceState.Success -> {
                        if (page == 1) {
                            productsList.clear()
                        }
                        _getAllSubCategorySearchProductsData.value =
                            getAllSubCategorySearchProductsResponse
                        productsList.addAll(getAllSubCategorySearchProductsResponse.data.data?.results.orEmpty())
                        currentPage.intValue =
                            getAllSubCategorySearchProductsResponse.data.data?.pagination?.currentPage!!.toInt() + 1
                        isLastPage.value =
                            getAllSubCategorySearchProductsResponse.data.data?.pagination?.hasNext == false
                    }

                    is ResourceState.Error -> {
                        _getAllSubCategorySearchProductsData.value =
                            getAllSubCategorySearchProductsResponse
                    }

                    else -> {}
                }

                isLoadingMore.value = false
            }

        }
    }
}