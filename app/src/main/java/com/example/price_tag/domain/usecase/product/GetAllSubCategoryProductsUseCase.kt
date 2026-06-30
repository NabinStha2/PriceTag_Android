package com.example.price_tag.domain.usecase.product

import com.example.price_tag.domain.model.product.Products
import com.example.price_tag.domain.repository.product.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSubCategoryProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(
        subCategoryId: Long,
        page: Int? = null,
        limit: Int? = null,
        sortBy: String? = null,
        order: String? = null,
    ): Flow<ResourceState<Products>> {
        return repository.getAllSubCategoryProducts(
            subCategoryId,
            page,
            limit,
            sortBy,
            order,
        )
    }
}
