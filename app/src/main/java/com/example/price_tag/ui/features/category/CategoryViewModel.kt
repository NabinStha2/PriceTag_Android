package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.domain.usecase.category.GetCategoriesUseCase
import com.example.price_tag.domain.usecase.category.GetSubCategoriesWithSelectedCategoryUseCase
import com.example.price_tag.domain.usecase.category.SetSelectedCategoryUseCase
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val navigationCoordinator: NavigationCoordinator,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesWithSelectedCategoryUseCase: GetSubCategoriesWithSelectedCategoryUseCase,
    private val setSelectedCategoryUseCase: SetSelectedCategoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryContract.State())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<CategoryContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(CategoryContract.Intent.GetAllCategories)
    }

    fun onIntent(intent: CategoryContract.Intent) {
        when (intent) {
            is CategoryContract.Intent.GetAllCategories -> getAllCategories()
            is CategoryContract.Intent.GetSubCategoriesWithSelectedCategory -> {
                getSubCategoriesWithSelectedCategory(
                    intent.category.id,
                    intent.index,
                    intent.category.name,
                )
            }
            is CategoryContract.Intent.OnSubCategoryClick -> {
                onSubCategoryClick(intent.subCategoryId)
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase().collectLatest { response ->
                _uiState.update { it.copy(categories = response) }
                if (response is ResourceState.Success && response.data.isNotEmpty()) {
                    val firstCategory = response.data.first()
                    setSelectedCategory(firstCategory.name)
                    getSubCategoriesWithSelectedCategory(
                        firstCategory.id,
                        0,
                        firstCategory.name,
                    )
                }
            }
        }
    }

    private fun getSubCategoriesWithSelectedCategory(
        categoryId: Long,
        index: Int,
        name: String,
    ) {
        if (_uiState.value.selectedCategoryId == categoryId) return

        _uiState.update { 
            it.copy(
                selectedCategoryId = categoryId,
                selectedIndex = index
            )
        }
        setSelectedCategory(name)
        viewModelScope.launch(Dispatchers.IO) {
            getSubCategoriesWithSelectedCategoryUseCase(categoryId).collectLatest { response ->
                _uiState.update { it.copy(subCategories = response) }
            }
        }
    }

    private fun setSelectedCategory(name: String) {
        setSelectedCategoryUseCase(name)
    }

    private fun onSubCategoryClick(subCategoryId: Long) {
        viewModelScope.launch {
//            _effect.send(CategoryContract.Effect.NavigateToProducts(subCategoryId))
            navigationCoordinator.navigate(NavigationItem.ProductView(subCategoryId = subCategoryId))
        }
    }
}
