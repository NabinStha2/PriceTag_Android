package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.domain.usecase.category.GetCategoriesUseCase
import com.example.price_tag.domain.usecase.category.GetSubCategoriesUseCase
import com.example.price_tag.domain.usecase.category.SetSelectedCategoryUseCase
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
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
    private val sharedPreferencesUtils: SharedPreferencesUtils,
    ) :
    ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils
    private val _getAllCategoriesData =
        MutableStateFlow<ResourceState<List<Category>>>(ResourceState.Initial())
    val getAllCategoriesData: StateFlow<ResourceState<List<Category>>> =
        _getAllCategoriesData

    private val _getSubCategoriesData =
        MutableStateFlow<ResourceState<List<SubCategory>>>(ResourceState.Initial())
    val getSubCategoriesData: StateFlow<ResourceState<List<SubCategory>>> =
        _getSubCategoriesData
    private var lastFetchedCategoryId: Long? = null


    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().collectLatest { response ->
                _getAllCategoriesData.value = response
            }
        }
    }

    fun getSubCategoriesWithSelectedCategory(categoryId: Long) {
        if (lastFetchedCategoryId != categoryId) {
            lastFetchedCategoryId = categoryId
            viewModelScope.launch {
                getSubCategoriesUseCase(categoryId).collectLatest { response ->
                    _getSubCategoriesData.value = response
                }
            }
        }
    }

    fun setSelectedCategory(name: String) {
        setSelectedCategoryUseCase(name)
    }

}
