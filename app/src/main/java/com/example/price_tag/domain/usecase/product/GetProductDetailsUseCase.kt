package com.example.price_tag.domain.usecase.product

import com.example.price_tag.data.model.product_details.SingleProductDetailsResponseDto
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.price_tag.domain.repository.product.ProductRepository
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
