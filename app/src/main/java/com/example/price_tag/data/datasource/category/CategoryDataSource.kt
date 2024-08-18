package com.example.price_tag.data.datasource.category

import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.model.category.SubCategoryResponse
import retrofit2.Response

interface CategoryDataSource {

    suspend fun getAllCategories(): Response<CategoryResponse>

    suspend fun getAllSubCategories(categoryId: Long): Response<SubCategoryResponse>

}