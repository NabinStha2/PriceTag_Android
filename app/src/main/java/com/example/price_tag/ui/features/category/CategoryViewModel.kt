package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.repository.category.CategoryRepository
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {

    private val _getAllCategoriesData =
        MutableStateFlow<ResourceState<CategoryResponse>>(ResourceState.Initial())
    val getAllCategoriesData: MutableStateFlow<ResourceState<CategoryResponse>> =
        _getAllCategoriesData

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getAllCategories().collectLatest { _getAllCategoriesResponse ->
                _getAllCategoriesData.value = _getAllCategoriesResponse
            }
        }
    }

}