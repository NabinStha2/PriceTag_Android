package com.example.price_tag.feature.category.domain.usecase

import com.example.price_tag.feature.category.domain.model.Category
import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<ResourceState<List<Category>>> {
        return repository.getAllCategories()
    }
}
