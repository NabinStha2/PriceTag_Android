package com.example.price_tag.feature.category.data.remote

import com.example.price_tag.core.network.ApiService
import com.example.price_tag.feature.category.data.model.CategoryResponse
import com.example.price_tag.feature.category.data.model.SubCategoryResponse
import retrofit2.Response
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    CategoryDataSource {

    override suspend fun getAllCategories(): Response<CategoryResponse> {
        return apiService.getAllCategories()
    }

    override suspend fun getSubCategoriesWithSelectedCategory(categoryId: Long): Response<SubCategoryResponse> {
        return apiService.getSubCategoriesWithSelectedCategory(categoryId)
    }


}