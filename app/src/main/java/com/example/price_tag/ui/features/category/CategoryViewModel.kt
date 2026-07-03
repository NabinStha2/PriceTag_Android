package com.example.price_tag.ui.features.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.domain.usecase.category.GetCategoriesUseCase
import com.example.price_tag.domain.usecase.category.GetSelectedCategoryUseCase
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
    private val getSelectedCategoryUseCase: GetSelectedCategoryUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryContract.State())
    val uiState = _uiState.asStateFlow()


    private val _effect = Channel<CategoryContract.Effect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(CategoryContract.Intent.GetAllCategories)
        observeSelectedCategoryName()
    }

    private fun observeSelectedCategoryName() {
        viewModelScope.launch(Dispatchers.IO) {
            getSelectedCategoryUseCase().collectLatest { name ->
                _uiState.update { it.copy(selectedCategoryName = name) }
            }
        }
    }

    fun onIntent(intent: CategoryContract.Intent) {
        when (intent) {
            is CategoryContract.Intent.GetAllCategories -> getAllCategories()
            is CategoryContract.Intent.RefreshCategories -> refreshCategories()
            is CategoryContract.Intent.GetSubCategoriesWithSelectedCategory -> {
                getSubCategoriesWithSelectedCategory(
                    intent.category.id,
                    intent.index,
                    intent.category.name,
                )
            }

            is CategoryContract.Intent.OnSubCategoryClick -> {
                onSubCategoryClick(
                    intent.subCategoryId,
                    intent.subCategoryName,
                )
            }

            is CategoryContract.Intent.RetryLastOperation -> retryLastOperation()

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
                selectedIndex = index,
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

    private fun refreshCategories() { // Clear cache and reload
        // Note: Cache invalidation would need to be handled differently
        // For now, just reload the data
        getAllCategories()
    }

    private fun retryLastOperation() { // Retry the last failed operation
        val currentState = _uiState.value
        when {
            currentState.categories is ResourceState.Error -> {
                getAllCategories()
            }

            currentState.subCategories is ResourceState.Error && currentState.selectedCategoryId != null -> { // Retry loading subcategories for currently selected category
                viewModelScope.launch(Dispatchers.IO) {
                    getSubCategoriesWithSelectedCategoryUseCase(currentState.selectedCategoryId).collectLatest { response ->
                        _uiState.update { it.copy(subCategories = response) }
                    }
                }
            }

            else -> { // Default to refreshing categories
                getAllCategories()
            }
        }
    }

    private fun onSubCategoryClick(
        subCategoryId: Long,
        subCategoryName: String,
    ) {
        viewModelScope.launch { //            _effect.send(CategoryContract.Effect.NavigateToProducts(subCategoryId))
            navigationCoordinator.navigate(NavigationItem.ProductView(
                subCategoryId = subCategoryId,
                subCategoryName = subCategoryName,
            ))
        }
    }
}
