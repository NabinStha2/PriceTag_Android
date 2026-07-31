package com.example.price_tag.feature.category.data.remote

import com.example.price_tag.feature.category.data.model.CategoryResponse
import com.example.price_tag.feature.category.data.model.SubCategoryResponse
import retrofit2.Response

interface CategoryDataSource {

    suspend fun getAllCategories(): Response<CategoryResponse>
    suspend fun getSubCategoriesWithSelectedCategory(categoryId : Long): Response<SubCategoryResponse>

//    suspend fun getAllSubCategories(categoryId: Long): Response<SubCategoryResponse>

}