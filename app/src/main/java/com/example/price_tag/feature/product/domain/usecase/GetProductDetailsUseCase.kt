package com.example.price_tag.feature.product.domain.usecase

import com.example.price_tag.feature.product.domain.model.SingleProductDetails
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Long): Flow<ResourceState<SingleProductDetails>> {
        return repository.getSingleProductDetails(productId)
    }
}
