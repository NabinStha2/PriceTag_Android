package com.example.price_tag.domain.usecase.category

import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.domain.repository.category.CategoryRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Long): Flow<ResourceState<List<SubCategory>>> {
        return repository.getSubCategoriesWithSelectedCategory(categoryId)
    }
}
