package com.example.price_tag.feature.product.domain.usecase

import com.example.price_tag.feature.product.domain.model.Products
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSubCategoryProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(
        subCategoryId: Long,
        page: Int? = 1,
        limit: Int? = 5,
        sortBy: String? = "created_at",
        order: String? = "asc",
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
