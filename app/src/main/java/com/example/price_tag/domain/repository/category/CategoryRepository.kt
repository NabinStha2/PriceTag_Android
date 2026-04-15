package com.example.price_tag.domain.repository.category

import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepository {
    // Shared State
    val selectedCategoryName: StateFlow<String?>
    fun setSelectedCategory(name: String)

    // Data Operations
    suspend fun getAllCategories(): Flow<ResourceState<List<Category>>>
    suspend fun getSubCategoriesWithSelectedCategory(categoryId: Long): Flow<ResourceState<List<SubCategory>>>
}
