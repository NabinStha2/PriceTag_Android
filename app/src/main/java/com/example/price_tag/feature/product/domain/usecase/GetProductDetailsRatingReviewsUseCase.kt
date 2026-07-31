package com.example.price_tag.feature.product.domain.usecase

import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviews
import com.example.price_tag.feature.product.domain.repository.ProductRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetailsRatingReviewsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(
        productId: Long,
        page: Int?,
        limit: Int?,
        sortBy: String?,
        order: String?,
    ): Flow<ResourceState<ProductDetailsRatingReviews>> {
        return repository.getProductDetailsRatingReviews(
            productId,
            page,
            limit,
            sortBy,
            order,
        )
    }
}
