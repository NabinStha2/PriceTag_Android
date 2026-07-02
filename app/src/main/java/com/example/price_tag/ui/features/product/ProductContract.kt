package com.example.price_tag.ui.features.product

import com.example.price_tag.domain.model.product.ProductData
import com.example.price_tag.domain.model.product.Products
import com.example.utilites.ResourceState

class ProductContract {

    sealed interface ProductIntent {
        data class GetAllSubCategoryProducts(
            val subCategoryId: Long,
            val page: Int? = 1,
            val limit: Int? = 10,
            val sortBy: String? = "createdAt",
            val order: String? = "asc",
        ) : ProductIntent

        data class GetAllSubCategorySearchProducts(
            val subCategoryId: Long,
            val searchQuery: String,
            val page: Int? = 1,
            val limit: Int? = 10,
            val sortBy: String? = "createdAt",
            val order: String? = "asc",
        ) : ProductIntent

        data class OnProductClicked(val productId: Long) : ProductIntent
    }

    data class ProductState(
        val getAllSubCategoryProductsData: ResourceState<Products> = ResourceState.Initial,
        val getAllSubCategorySearchProductsData: ResourceState<Products> = ResourceState.Initial,
        var productListData: List<ProductData> = emptyList(),
        val isLoadingMore: Boolean = false,
        val currentPage: Int = 0,
        val isLastPage: Boolean = false,
    )


    sealed interface ProductEffect {
        data class NavigateToProductDetails(val productId: Long) : ProductEffect
    }

}