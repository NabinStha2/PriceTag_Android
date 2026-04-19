package com.example.price_tag.data.repository.category

import com.example.price_tag.data.remote.datasource.category.CategoryDataSource
import com.example.price_tag.data.mapper.category.toDomain
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.domain.repository.category.CategoryRepository
import com.example.price_tag.core.utils.SharedPreferencesUtils
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    private val moshi: Moshi
) : CategoryRepository {

    // Shared State Implementation
    private val _selectedCategoryName = MutableStateFlow<String?>(null)
    override val selectedCategoryName: StateFlow<String?> = _selectedCategoryName.asStateFlow()

    override fun setSelectedCategory(name: String) {
        _selectedCategoryName.value = name
    }

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter(CommonErrorResponse::class.java)

    override suspend fun getAllCategories(): Flow<ResourceState<List<Category>>> = flow {
        emit(ResourceState.Loading())
        val token = sharedPreferencesUtils.getValueString(sharedPreferencesUtils.bearerToken) ?: ""
        val response = categoryDataSource.getAllCategories("Bearer $token")
        if (response.isSuccessful && response.body() != null) {
            val categories = response.body()!!.data?.map { it.toDomain() } ?: emptyList()
            emit(ResourceState.Success(categories))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Failed to fetch categories"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }

    override suspend fun getSubCategoriesWithSelectedCategory(categoryId: Long): Flow<ResourceState<List<SubCategory>>> = flow {
        emit(ResourceState.Loading())
        val response = categoryDataSource.getSubCategoriesWithSelectedCategory(categoryId)
        if (response.isSuccessful && response.body() != null) {
            val subCategories = response.body()!!.data?.results?.map { it.toDomain() } ?: emptyList()
            emit(ResourceState.Success(subCategories))
        } else {
            val errorBody = response.errorBody()?.string()
            val commonErrorResponse = errorBody?.let { jsonErrorAdapter.fromJson(it) }
            emit(ResourceState.Error(commonErrorResponse?.message ?: "Failed to fetch sub-categories"))
        }
    }.catch { e ->
        emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
    }
}
