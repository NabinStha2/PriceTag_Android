package com.example.price_tag.ui.features.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.ui.features.category.components.CategoryNavigationRail
import com.example.price_tag.ui.features.category.components.SubCategoryGrid
import com.example.utilites.ResourceState

@Composable
fun CategoryView(
    padding: PaddingValues,
    categoryViewModel: CategoryViewModel,
) {
    val uiState by categoryViewModel.uiState.collectAsStateWithLifecycle()

    CategoryView(
        uiState = uiState,
        padding = padding,
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
    padding: PaddingValues,
    onCategorySelected: (Int, Category) -> Unit,
    onSubCategoryClick: (Long, String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    when (val categoriesState = uiState.categories) {
        is ResourceState.Loading -> CategoriesLoadingState(padding)

        is ResourceState.Error -> CategoriesErrorState(errorMessage = categoriesState.error.toString())

        is ResourceState.Success -> CategoriesSuccessState(
            categories = categoriesState.data,
            subCategoriesState = uiState.subCategories,
            selectedCategoryName = uiState.selectedCategoryName,
            selectedIndex = uiState.selectedIndex,
            padding = padding,
            onCategorySelected = onCategorySelected,
            onSubCategoryClick = onSubCategoryClick,
            onRefresh = onRefresh,
            onRetry = onRetry,
        )

        else -> {}
    }
}

@Composable
private fun CategoriesLoadingState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CategoriesErrorState(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
    }
}

@Composable
private fun CategoriesSuccessState(
    categories: List<Category>,
    subCategoriesState: ResourceState<List<SubCategory>>,
    selectedCategoryName: String?,
    selectedIndex: Int,
    padding: PaddingValues,
    onCategorySelected: (Int, Category) -> Unit,
    onSubCategoryClick: (Long, String) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    Row(modifier = Modifier
        .padding(padding)
        .padding(top = 16.dp)) {
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

        CategoryView(uiState = state,
                     padding = PaddingValues(0.dp),
                     onCategorySelected = { _, _ -> },
                     onSubCategoryClick = { _, _ -> },
                     onRefresh = {},
                     onRetry = {})
    }
}
