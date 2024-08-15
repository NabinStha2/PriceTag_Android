package com.example.price_tag.data.datasource.category

import com.example.price_tag.data.model.category.CategoryResponse
import retrofit2.Response

interface CategoryDataSource {

    suspend fun getAllCategories(): Response<CategoryResponse>

}