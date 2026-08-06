package com.example.price_tag.feature.category.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.core.ui.components.ErrorState
import com.example.price_tag.core.ui.viewmodel.SelectedCategoryViewModel
import com.example.price_tag.feature.cart.presentation.CartViewModel
import com.example.price_tag.feature.category.domain.model.Category
import com.example.price_tag.feature.category.domain.model.SubCategory
import com.example.price_tag.feature.category.presentation.components.CategoryNavigationRail
import com.example.price_tag.feature.category.presentation.components.SubCategoryGrid
import com.example.utilites.ResourceState

@Composable
fun CategoryView(
    categoryViewModel: CategoryViewModel,
    cartViewModel: CartViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val uiState by categoryViewModel.uiState.collectAsStateWithLifecycle()

    CategoryView(
        //        onIntent = {
        //            categoryViewModel.onIntent(it)
        //        },
        onIntent = categoryViewModel::onIntent,
        uiState = uiState,
        onCategorySelected = { index, category ->
            categoryViewModel.onIntent(CategoryContract.Intent.GetSubCategoriesWithSelectedCategory(category = category,
                                                                                                    index = index))
        },
        onSubCategoryClick = { id, name ->
            categoryViewModel.onIntent(CategoryContract.Intent.OnSubCategoryClick(
                id,
                name,
            ))
        },
        onRefresh = {
            categoryViewModel.onIntent(CategoryContract.Intent.RefreshCategories)
        },
        onRetry = {
            categoryViewModel.onIntent(CategoryContract.Intent.RetryLastOperation)
        },
    )
}

@Composable
private fun CategoryView(
    uiState: CategoryContract.State,
    onCategorySelected: (Int, Category) -> Unit,
    onSubCategoryClick: (Long, String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onIntent: (CategoryContract.Intent) -> Unit,
) {
    when (val categoriesState = uiState.categories) {
        is ResourceState.Loading -> CategoriesLoadingState()

        is ResourceState.Error -> CategoriesErrorState(
            errorMessage = categoriesState.error,
            onRetryAction = { onIntent(CategoryContract.Intent.RefreshCategories) },
        )

        is ResourceState.Success -> CategoriesSuccessState(
            categories = categoriesState.data,
            subCategoriesState = uiState.subCategories,
            selectedCategoryName = uiState.selectedCategoryName,
            selectedIndex = uiState.selectedIndex,
            onCategorySelected = onCategorySelected,
            onSubCategoryClick = onSubCategoryClick,
            onRefresh = onRefresh,
            onRetry = onRetry,
        )

        else -> {}
    }
}

@Composable
private fun CategoriesLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CategoriesErrorState(
    errorMessage: String,
    onRetryAction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        ErrorState(
            errorMessage = errorMessage,
            onRetry = onRetryAction,
        )
    }
}

@Composable
private fun CategoriesSuccessState(
    categories: List<Category>,
    subCategoriesState: ResourceState<List<SubCategory>>,
    selectedCategoryName: String?,
    selectedIndex: Int,
    onCategorySelected: (Int, Category) -> Unit,
    onSubCategoryClick: (Long, String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp)) {
        CategoryNavigationRail(
            categories = categories,
            selectedIndex = selectedIndex,
            onCategorySelected = onCategorySelected,
        )

        Spacer(modifier = Modifier.width(10.dp))

        SubCategoryGrid(
            subCategoriesState = subCategoriesState,
            selectedCategoryName = selectedCategoryName,
            onSubCategoryClick = onSubCategoryClick,
            onRefresh = onRefresh,
            onRetry = onRetry,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryViewPreview() {
    PriceTagTheme {
        val sampleCategories = listOf(
            Category(id = 1,
                     name = "Electronics",
                     imageUrl = null),
            Category(id = 2,
                     name = "Clothing",
                     imageUrl = null),
            Category(id = 3,
                     name = "Food",
                     imageUrl = null),
            Category(id = 4,
                     name = "Books",
                     imageUrl = null),
            Category(id = 5,
                     name = "Sports",
                     imageUrl = null),
        )

        val sampleSubCategories = listOf(
            SubCategory(id = 1,
                        name = "Phones",
                        categoryId = 1,
                        imageUrl = null),
            SubCategory(id = 2,
                        name = "Laptops",
                        categoryId = 1,
                        imageUrl = null),
            SubCategory(id = 3,
                        name = "Watches",
                        categoryId = 1,
                        imageUrl = null),
            SubCategory(id = 4,
                        name = "Headphones",
                        categoryId = 1,
                        imageUrl = null),
            SubCategory(id = 5,
                        name = "Cameras",
                        categoryId = 1,
                        imageUrl = null),
            SubCategory(id = 6,
                        name = "Tablets",
                        categoryId = 1,
                        imageUrl = null),
        )

        val state = CategoryContract.State(categories = ResourceState.Success(sampleCategories),
                                           subCategories = ResourceState.Success(sampleSubCategories),
                                           selectedIndex = 0)

        // Preview needs dummy viewmodels or updated signature
//         CategoryView(uiState = state, ...)
    }
}
