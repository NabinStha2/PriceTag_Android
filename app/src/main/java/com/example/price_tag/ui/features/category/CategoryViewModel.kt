package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.domain.usecase.category.GetCategoriesUseCase
import com.example.price_tag.domain.usecase.category.GetSubCategoriesWithSelectedCategoryUseCase
import com.example.price_tag.domain.usecase.category.SetSelectedCategoryUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val navigationCoordinator: NavigationCoordinator,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesWithSelectedCategoryUseCase: GetSubCategoriesWithSelectedCategoryUseCase,
    private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
) : ViewModel() {
    private val _getAllCategoriesData =
        MutableStateFlow<ResourceState<List<Category>>>(ResourceState.Initial())
    val getAllCategoriesData: StateFlow<ResourceState<List<Category>>> =
        _getAllCategoriesData.asStateFlow()

    private val _getSubCategoriesWithSelectedCategoryData =
        MutableStateFlow<ResourceState<List<SubCategory>>>(ResourceState.Initial())
    val getSubCategoriesWithSelectedCategoryData: StateFlow<ResourceState<List<SubCategory>>> =
        _getSubCategoriesWithSelectedCategoryData.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex = _selectedIndex.asStateFlow()

    init {
        getAllCategories()
    }


    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().collectLatest { response ->
                _getAllCategoriesData.value = response
                if (response is ResourceState.Success && response.data.isNotEmpty()) {
                    setSelectedCategory(response.data.first().name)
                    getSubCategoriesWithSelectedCategory(
                        response.data.first().id,
                        0,
                        response.data.first().name,
                    )
                }
            }
        }
    }

    fun getSubCategoriesWithSelectedCategory(
        categoryId: Long,
        index: Int,
        name: String,
    ) {
        if (_selectedCategoryId.value == categoryId) return

        _selectedCategoryId.value = categoryId
        _selectedIndex.value = index
        setSelectedCategory(name)
        viewModelScope.launch {
            getSubCategoriesWithSelectedCategoryUseCase(categoryId).collectLatest { response ->
                _getSubCategoriesWithSelectedCategoryData.value = response

            }
        }
    }

    fun setSelectedCategory(name: String) {
        setSelectedCategoryUseCase(name)
    }

    fun onSubCategoryClick(subCategoryId: Long) {
        viewModelScope.launch {
            navigationCoordinator.navigate(NavigationItem.ProductView(subCategoryId = subCategoryId))
        }

    }

}
