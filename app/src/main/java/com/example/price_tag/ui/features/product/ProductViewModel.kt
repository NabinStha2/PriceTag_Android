package com.example.price_tag.ui.features.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.domain.usecase.product.GetAllSubCategoryProductsUseCase
import com.example.price_tag.domain.usecase.product.GetAllSubCategorySearchProductsUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
class ProductViewModel @Inject constructor(
    private val getAllSubCategoryProductsUseCase: GetAllSubCategoryProductsUseCase,
    private val getAllSubCategorySearchProductsUseCase: GetAllSubCategorySearchProductsUseCase,
    private val navigationCoordinator: NavigationCoordinator,
) : ViewModel() {

    private val _productState: MutableStateFlow<ProductContract.ProductState> =
        MutableStateFlow(ProductContract.ProductState())
    val productState: StateFlow<ProductContract.ProductState> = _productState.asStateFlow()

    private val _effect = MutableSharedFlow<ProductContract.ProductEffect>()
    val effect = _effect.asSharedFlow()

    private var paginationJob: Job? = null


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

            is ProductContract.ProductIntent.OnProductClicked -> {
                onProductClick(productIntent.productId)
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
        paginationJob?.cancel()

        paginationJob = viewModelScope.launch(Dispatchers.IO) {
            if (_productState.value.isLoadingMore) return@launch

            getAllSubCategoryProductsUseCase(
                subCategoryId,
                page,
                limit,
                sortBy,
                order,
            ) //                .onStart {
                //                _productState.update {
                //                    it.copy(
                //                        isLoadingMore = true,
                //                        isLastPage = false,
                //                    )
                //                }
                //            }
                .collectLatest { response ->
                    _productState.update { state ->
                        when (response) {
                            is ResourceState.Loading -> {
                                state.copy(
                                    isLoadingMore = true,
                                    isLastPage = false,
                                )
                            }

                            is ResourceState.Success -> {
                                val newList = if (page == 1) {
                                    response.data.products
                                } else { //                                    buildList {
                                    //                                        addAll(state.productListData)
                                    //                                        addAll(response.data.products)
                                    //                                    }
                                    state.productListData + response.data.products
                                }
                                state.copy(
                                    isLoadingMore = false,
                                    getAllSubCategoryProductsData = response,
                                    productListData = newList.distinctBy { it.id }, // distinct by product id so while pagination there will be no duplicate products in the list
                                    currentPage = response.data.pagination.currentPage,
                                    isLastPage = !response.data.pagination.hasNext,
                                )
                            }

                            is ResourceState.Error -> {
                                state.copy(
                                    isLoadingMore = false,
                                    getAllSubCategoryProductsData = response,
                                )
                            }

                            else -> {
                                state.copy(isLoadingMore = false)
                            }
                        }

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
            if (_productState.value.isLoadingMore) return@launch

            getAllSubCategorySearchProductsUseCase(
                subCategoryId,
                page,
                limit,
                sortBy,
                order,
                name,
            ).collectLatest { response ->
                _productState.update { state ->
                    when (response) {
                        is ResourceState.Loading -> {
                            state.copy(
                                isLoadingMore = true,
                                isLastPage = false,
                            )
                        }

                        is ResourceState.Success -> {
                            val newList = if (page == 1) {
                                response.data.products
                            } else {
                                state.productListData + response.data.products
                            }
                            state.copy(
                                isLoadingMore = false,
                                getAllSubCategorySearchProductsData = response,
                                productListData = newList.distinctBy { it.id },
                                currentPage = response.data.pagination.currentPage,
                                isLastPage = !response.data.pagination.hasNext,
                            )
                        }

                        is ResourceState.Error -> {
                            state.copy(
                                getAllSubCategorySearchProductsData = response,
                                isLoadingMore = false,
                            )
                        }

                        else ->     {
                            state.copy(isLoadingMore = false)
                        }
                    }
                }
            }

        }
    }

    private fun onProductClick(productId: Long) {
        viewModelScope.launch {
            _effect.emit(ProductContract.ProductEffect.NavigateToProductDetails(productId))
        }
    }


}
