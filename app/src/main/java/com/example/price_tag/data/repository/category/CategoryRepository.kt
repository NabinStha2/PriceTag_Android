package com.example.price_tag.data.repository.category

import android.util.Log
import com.example.price_tag.data.datasource.category.CategoryDataSource
import com.example.price_tag.data.model.CommonErrorResponse
import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.model.category.SubCategoryResponse
import com.example.price_tag.utils.SharedPreferencesUtils
import com.example.utilites.ResourceState
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class CategoryRepository @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
    private val sharedPreferencesUtils: SharedPreferencesUtils
) {
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
        moshi.adapter<CommonErrorResponse>()

    suspend fun getAllCategories(): Flow<ResourceState<CategoryResponse>> {
        return flow {
            emit(ResourceState.Loading())
//            Log.i("CategoryRepository GetAllCategories", "bearerToken => ${sharedPreferencesUtils.getValueString("bearerToken")}")
            val response = categoryDataSource.getAllCategories(
                "Bearer ${
                    sharedPreferencesUtils.getValueString("bearerToken") ?: ""
                }"
            )
//            Log.i(
//                "CategoryRepository GetAllCategories",
//                "response => ${response.errorBody()?.string()}"
//            )
            if (response.isSuccessful && response.body() != null) emit(
                ResourceState.Success(
                    response.body()!!
                )
            )
            else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                Log.e(
                    "CategoryRepository GetAllCategories",
                    commonErrorResponse?.message ?: "Error while logging in"
                )
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while logging in"))
            }
        }.catch { err ->
            Log.e(
                "CategoryRepository GetAllCategories",
                err.toString() ?: "Error while logging in"
            )
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }

    suspend fun getSubCategoriesWithSelectedCategory(categoryId: Long): Flow<ResourceState<SubCategoryResponse>> {
        return flow {
            emit(ResourceState.Loading())
            val response = categoryDataSource.getSubCategoriesWithSelectedCategory(categoryId)
            if (response.isSuccessful && response.body() != null) {
                emit(
                    ResourceState.Success(
                        response.body()!!
                    )
                )
            } else {
                val commonErrorResponse: CommonErrorResponse? =
                    response.errorBody()?.string()?.let { jsonErrorAdapter.fromJson(it) }
                Log.e(
                    "CategoryRepository getSubCategoriesWithSelectedCategory",
                    commonErrorResponse?.message ?: "Error while logging in"
                )
                emit(ResourceState.Error(commonErrorResponse?.message ?: "Error while logging in"))
            }
        }.catch { err ->
            Log.e(
                "CategoryRepository getSubCategoriesWithSelectedCategory",
                err.localizedMessage ?: "Error while logging in"
            )
            emit(ResourceState.Error(err.localizedMessage ?: "Something went wrong"))
        }
    }

}