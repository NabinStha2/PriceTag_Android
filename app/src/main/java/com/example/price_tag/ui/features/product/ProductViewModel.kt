package com.example.price_tag.ui.features.product

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.domain.model.product.ProductData
import com.example.price_tag.domain.model.product.Products
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getAllSubCategoryProductsUseCase: GetAllSubCategoryProductsUseCase,
                                           private val getAllSubCategorySearchProductsUseCase: GetAllSubCategorySearchProductsUseCase,
                                           private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
                                           private val navigationCoordinator: NavigationCoordinator) :
    ViewModel() {

    val productsList = mutableStateListOf<ProductData>()
    val isLoadingMore = mutableStateOf(false)
    var isLastPage = mutableStateOf(false)
    var currentPage = mutableIntStateOf(1)

    val selectedCategoryName: StateFlow<String?> = getSelectedCategoryUseCase()

    private val _getAllSubCategoryProductsData =
        MutableStateFlow<ResourceState<Products>>(ResourceState.Initial)
    val getAllSubCategoryProductsData: StateFlow<ResourceState<Products>> =
        _getAllSubCategoryProductsData.asStateFlow()

    private val _getAllSubCategorySearchProductsData =
        MutableStateFlow<ResourceState<Products>>(ResourceState.Initial)
    val getAllSubCategorySearchProductsData: StateFlow<ResourceState<Products>> =
        _getAllSubCategorySearchProductsData.asStateFlow()


    fun onProductClick(productId: Long) { //        navigationCoordinator.navigateTo(NavigationItem.ProductDetailsView(productId))
    }

    fun getAllSubCategoryProducts(subCategoryId: Long,
                                  page: Int? = 1,
                                  limit: Int? = 6,
                                  sortBy: String? = "createdAt",
                                  order: String? = "asc") {
        viewModelScope.launch(Dispatchers.IO) {
            if (isLoadingMore.value) return@launch

            isLoadingMore.value = true
            isLastPage.value = false

            getAllSubCategoryProductsUseCase(subCategoryId,
                                             page,
                                             limit,
                                             sortBy,
                                             order).collectLatest { response ->
                when (response) {
                    is ResourceState.Success -> {
                        if (page == 1) {
                            productsList.clear()
                        }
                        _getAllSubCategoryProductsData.value = response
                        productsList.addAll(response.data.products)
                        currentPage.intValue = response.data.pagination.currentPage + 1
                        isLastPage.value =
                            currentPage.intValue > response.data.pagination.totalPages
                    }

                    is ResourceState.Error -> {
                        _getAllSubCategoryProductsData.value = response
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

            getAllSubCategorySearchProductsUseCase(subCategoryId,
                                                   page,
                                                   limit,
                                                   sortBy,
                                                   order,
                                                   name).collectLatest { response ->
                when (response) {
                    is ResourceState.Success -> {
                        if (page == 1) {
                            productsList.clear()
                        }
                        _getAllSubCategorySearchProductsData.value = response
                        productsList.addAll(response.data.products)
                        currentPage.intValue = response.data.pagination.currentPage + 1
                        isLastPage.value =
                            currentPage.intValue > response.data.pagination.totalPages
                    }

                    is ResourceState.Error -> {
                        _getAllSubCategorySearchProductsData.value = response
                    }

                    else -> {}
                }

                isLoadingMore.value = false
            }

        }
    }

}
