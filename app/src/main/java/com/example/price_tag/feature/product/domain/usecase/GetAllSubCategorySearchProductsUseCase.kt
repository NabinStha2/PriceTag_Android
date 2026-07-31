package com.example.price_tag.feature.product.domain.usecase

import com.example.price_tag.feature.product.domain.model.Products
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSubCategorySearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        subCategoryId: Long,
        page: Int? = null,
        limit: Int? = null,
        sortBy: String? = null,
        order: String? = null,
        name: String? = null
    ): Flow<ResourceState<Products>> {
        return repository.getAllSubCategorySearchProducts(subCategoryId, page, limit, sortBy, order, name)
    }
}
