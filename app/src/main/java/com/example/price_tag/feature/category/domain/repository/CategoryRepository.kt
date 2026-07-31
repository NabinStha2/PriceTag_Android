package com.example.price_tag.feature.category.domain.repository

import com.example.price_tag.feature.category.domain.model.Category
import com.example.price_tag.feature.category.domain.model.SubCategory
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
