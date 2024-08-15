package com.example.price_tag.data.datasource.category

import com.example.price_tag.data.api.ApiService
import com.example.price_tag.data.model.category.CategoryResponse
import retrofit2.Response
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    CategoryDataSource {

    override suspend fun getAllCategories(): Response<CategoryResponse> {
        return apiService.getAllCategories()
    }

}