package com.example.price_tag.feature.category.data.repository

import android.util.Log
import com.example.price_tag.feature.category.data.mapper.toDomain
import com.example.price_tag.core.common.data.model.CommonErrorResponse
import com.example.price_tag.feature.category.data.remote.CategoryDataSource
import com.example.price_tag.feature.category.domain.model.Category
import com.example.price_tag.feature.category.domain.model.SubCategory
import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import com.example.utilites.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
    private val json: Json,
) : CategoryRepository {
    // Shared State Implementation
    private val _selectedCategoryName = MutableStateFlow<String?>(null)
    override val selectedCategoryName: StateFlow<String?> = _selectedCategoryName.asStateFlow()

    override fun setSelectedCategory(name: String) {
        _selectedCategoryName.value = name
    }

    //    private val jsonErrorAdapter: JsonAdapter<CommonErrorResponse> =
    //        moshi.adapter(CommonErrorResponse::class.java)

    // Cache management
    private var categoriesCache: List<Category>? = null
    private var categoriesCacheTimestamp: Long = 0
    private val subCategoriesCache = mutableMapOf<Long, Pair<List<SubCategory>, Long>>()

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val INITIAL_RETRY_DELAY = 1000L // 1 second
        private const val CACHE_DURATION_MILLIS = 30 * 60 * 1000L // 30 minutes
    }

    /**
     * Checks if the categories cache is still valid
     */
    private fun isCategoriesCacheValid(): Boolean {
        return categoriesCache != null && (System.currentTimeMillis() - categoriesCacheTimestamp) < CACHE_DURATION_MILLIS
    }

    /**
     * Checks if the sub-categories cache for a specific category is still valid
     */
    private fun isSubCategoriesCacheValid(categoryId: Long): Boolean {
        val cachedData = subCategoriesCache[categoryId]
        return cachedData != null && (System.currentTimeMillis() - cachedData.second) < CACHE_DURATION_MILLIS
    }

    /**
     * Invalidates all caches (use when user logs out or needs to refresh)
     */
    fun invalidateAllCaches() {
        categoriesCache = null
        categoriesCacheTimestamp = 0
        subCategoriesCache.clear()
    }

    /**
     * Invalidates sub-categories cache for a specific category
     */
    fun invalidateSubCategoriesCache(categoryId: Long) {
        subCategoriesCache.remove(categoryId)
    }

    /**
     * Determines if a throwable is retryable (network-related errors only)
     */
    private fun Throwable.isRetryable(): Boolean {
        return this is java.io.IOException
    }

    /**
     * Calculates exponential backoff delay for retry attempts
     */
    private fun getRetryDelay(attemptNumber: Long): Long {
        return INITIAL_RETRY_DELAY * (1 shl attemptNumber.toInt()) // 1s, 2s, 4s
    }

    override suspend fun getAllCategories(): Flow<ResourceState<List<Category>>> =
        flow { // Check cache first
            if (isCategoriesCacheValid()) {
                Log.i("getAllCategories",
                      "getAllCategories: Returning cached categories (timestamp: $categoriesCacheTimestamp, age: ${System.currentTimeMillis() - categoriesCacheTimestamp} ms)")
                emit(ResourceState.Success(categoriesCache ?: emptyList()))
                return@flow
            }
            emit(ResourceState.Loading)
            val response = categoryDataSource.getAllCategories()
            if (response.isSuccessful && response.body() != null) {
                val categories =
                    response.body()!!.data?.map { it.toDomain() } ?: emptyList() // Update cache
                categoriesCache = categories
                categoriesCacheTimestamp = System.currentTimeMillis()
                emit(ResourceState.Success(categories))
            } else {
                val errorBody = response.errorBody()
                    ?.string()
                val message = parseError(errorBody) ?: "Failed to fetch categories"
                emit(ResourceState.Error(message))
            }
        }.retryWhen { cause, attempt ->
            if (attempt < MAX_RETRY_ATTEMPTS && cause.isRetryable()) {
                delay(getRetryDelay(attempt))
                true
            } else {
                false
            }
        }
            .catch { e ->
                emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
            }

    override suspend fun getSubCategoriesWithSelectedCategory(categoryId: Long): Flow<ResourceState<List<SubCategory>>> =
        flow { // Check cache first
            if (isSubCategoriesCacheValid(categoryId)) {
                val cachedData = subCategoriesCache[categoryId]
                if (cachedData != null) {
                    emit(ResourceState.Success(cachedData.first))
                    return@flow
                }
            }

            emit(ResourceState.Loading)
            val response = categoryDataSource.getSubCategoriesWithSelectedCategory(categoryId)
            if (response.isSuccessful && response.body() != null) {
                val subCategories = response.body()!!.data?.results?.map { it.toDomain() }
                                    ?: emptyList() // Update cache with timestamp
                subCategoriesCache[categoryId] = Pair(subCategories,
                                                      System.currentTimeMillis())
                emit(ResourceState.Success(subCategories))
            } else {
                val errorBody = response.errorBody()
                    ?.string()
                val message = parseError(errorBody) ?: "Failed to fetch sub-categories"
                emit(ResourceState.Error(message))
            }
        }.retryWhen { cause, attempt ->
            if (attempt < MAX_RETRY_ATTEMPTS && cause.isRetryable()) {
                delay(getRetryDelay(attempt))
                true
            } else {
                false
            }
        }
            .catch { e ->
                emit(ResourceState.Error(e.localizedMessage ?: "An error occurred"))
            }

    private fun parseError(errorBody: String?): String? = errorBody?.let {
        try {
            json.decodeFromString<CommonErrorResponse>(it).message
        } catch (_: Exception) {
            null
        }
    }
}
