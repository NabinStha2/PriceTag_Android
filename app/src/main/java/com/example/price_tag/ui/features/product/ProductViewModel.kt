package com.example.price_tag.ui.features.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.domain.usecase.category.GetSelectedCategoryUseCase
import com.example.price_tag.domain.usecase.product.GetAllSubCategoryProductsUseCase
import com.example.price_tag.domain.usecase.product.GetAllSubCategorySearchProductsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllSubCategoryProductsUseCase: GetAllSubCategoryProductsUseCase,
    private val getAllSubCategorySearchProductsUseCase: GetAllSubCategorySearchProductsUseCase,
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
) : ViewModel() {

    private val _productState: MutableStateFlow<ProductContract.ProductState> =
        MutableStateFlow(ProductContract.ProductState())
    val productState: StateFlow<ProductContract.ProductState> = _productState.asStateFlow()

    //    val productsList = mutableStateListOf<ProductData>()
    //    val isLoadingMore = mutableStateOf(false)
    //    var isLastPage = mutableStateOf(false)
    //    var currentPage = mutableIntStateOf(1)

    //    val selectedCategoryName: StateFlow<String?> = getSelectedCategoryUseCase()

    //    private val _getAllSubCategoryProductsData =
    //        MutableStateFlow<ResourceState<Products>>(ResourceState.Initial)
    //    val getAllSubCategoryProductsData: StateFlow<ResourceState<Products>> =
    //        _getAllSubCategoryProductsData.asStateFlow()
    //
    //    private val _getAllSubCategorySearchProductsData =
    //        MutableStateFlow<ResourceState<Products>>(ResourceState.Initial)
    //    val getAllSubCategorySearchProductsData: StateFlow<ResourceState<Products>> =
    //        _getAllSubCategorySearchProductsData.asStateFlow()


    fun onIntent(productIntent: ProductContract.ProductIntent) {
        when (productIntent) {
            is ProductContract.ProductIntent.GetAllSubCategoryProducts -> {
                getAllSubCategoryProducts(
                    subCategoryId = productIntent.subCategoryId,
                    page = productIntent.page,
                    limit = productIntent.limit,
                    sortBy = productIntent.sortBy,
                    order = productIntent.order,
                )
            }

            is ProductContract.ProductIntent.GetAllSubCategorySearchProducts -> {
                getAllSubCategorySearchProductsData(
                    subCategoryId = productIntent.subCategoryId,
                    page = productIntent.page,
                    limit = productIntent.limit,
                    sortBy = productIntent.sortBy,
                    order = productIntent.order,
                    name = productIntent.searchQuery,
                )
            }
        }
    }


    private fun getAllSubCategoryProducts(
        subCategoryId: Long,
        page: Int? = 1,
        limit: Int? = 10,
        sortBy: String? = "createdAt",
        order: String? = "asc",
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_productState.value.isLoadingMore || _productState.value.isLastPage) return@launch

            _productState.update {
                it.copy(
                    isLoadingMore = true,
                    isLastPage = false,
                )
            }

            getAllSubCategoryProductsUseCase(
                subCategoryId,
                page,
                limit,
                sortBy,
                order,
            ).collectLatest { response ->
                when (response) {
                    is ResourceState.Success -> {
                        if (page == 1) {
                            _productState.update {
                                it.copy(productListData = emptyList())
                            }
                        }
                        _productState.update {
                            it.copy(
                                getAllSubCategoryProductsData = response,
                                productListData = it.productListData + response.data.products,
                                currentPage = response.data.pagination.currentPage,
                                isLastPage = !response.data.pagination.hasNext,
                            )
                        }
                    }

                    is ResourceState.Error -> {
                        _productState.update {
                            it.copy(getAllSubCategoryProductsData = response)
                        }
                    }

                    else -> {}
                }
                _productState.update {
                    it.copy(isLoadingMore = false)
                }
            }
        }
    }

    private fun getAllSubCategorySearchProductsData(
        subCategoryId: Long,
        page: Int? = 1,
        limit: Int? = 10,
        sortBy: String? = "createdAt",
        order: String? = "asc",
        name: String? = "",
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_productState.value.isLoadingMore || _productState.value.isLastPage) return@launch
            _productState.update {
                it.copy(
                    isLoadingMore = true,
                    isLastPage = false,
                )
            }

            getAllSubCategorySearchProductsUseCase(subCategoryId,
                                                   page,
                                                   limit,
                                                   sortBy,
                                                   order,
                                                   name).collectLatest { response ->
                when (response) {
                    is ResourceState.Success -> {
                        if (page == 1) {
                            _productState.update {
                                it.copy(productListData = emptyList())
                            }
                        }
                        _productState.update {
                            it.copy(
                                getAllSubCategorySearchProductsData = response,
                                productListData = it.productListData + response.data.products,
                                currentPage = response.data.pagination.currentPage,
                                isLastPage = !response.data.pagination.hasNext,
                            )
                        }

                    }

                    is ResourceState.Error -> {
                        _productState.update {
                            it.copy(getAllSubCategorySearchProductsData = response)
                        } //                        _getAllSubCategorySearchProductsData.value = response
                    }

                    else -> {}
                }
                _productState.update {
                    it.copy(isLoadingMore = false)
                }
            }

        }
    }

    fun onProductClick(productId: Long) { //        navigationCoordinator.navigateTo(NavigationItem.ProductDetailsView(productId))
    }


}
