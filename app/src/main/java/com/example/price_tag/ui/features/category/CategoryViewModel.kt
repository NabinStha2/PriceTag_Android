package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.model.category.SubCategoryResponse
import com.example.price_tag.data.repository.category.CategoryRepository
import com.example.price_tag.utils.SharedPreferencesUtils
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    ) :
    ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils
    private val _getAllCategoriesData =
        MutableStateFlow<ResourceState<CategoryResponse>>(ResourceState.Initial())
    val getAllCategoriesData: StateFlow<ResourceState<CategoryResponse>> =
        _getAllCategoriesData

    private val _getSubCategoriesData =
        MutableStateFlow<ResourceState<SubCategoryResponse>>(ResourceState.Initial())
    val getSubCategoriesData: StateFlow<ResourceState<SubCategoryResponse>> =
        _getSubCategoriesData
    private var lastFetchedCategoryId: Long? = null


    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getAllCategories().collectLatest { _getAllCategoriesResponse ->
                _getAllCategoriesData.value = _getAllCategoriesResponse
            }
        }
    }

    fun getSubCategoriesWithSelectedCategory(categoryId: Long) {
        if (lastFetchedCategoryId != categoryId) {
            lastFetchedCategoryId = categoryId
            viewModelScope.launch {
                categoryRepository.getSubCategoriesWithSelectedCategory(categoryId)
                    .collectLatest { _getSubCategoriesResponse ->
                        _getSubCategoriesData.value = _getSubCategoriesResponse
                    }
            }
        }
    }

}