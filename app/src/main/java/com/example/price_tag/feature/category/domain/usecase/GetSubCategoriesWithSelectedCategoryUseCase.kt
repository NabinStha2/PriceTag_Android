package com.example.price_tag.feature.category.domain.usecase

import com.example.price_tag.feature.category.domain.model.SubCategory
import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoriesWithSelectedCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Long): Flow<ResourceState<List<SubCategory>>> {
        return repository.getSubCategoriesWithSelectedCategory(categoryId)
    }
}
