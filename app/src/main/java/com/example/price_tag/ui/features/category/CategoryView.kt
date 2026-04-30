package com.example.price_tag.ui.features.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    when (val categoriesState = uiState.categories) {
        is ResourceState.Loading -> CategoriesLoadingState(padding)

        is ResourceState.Error -> CategoriesErrorState(errorMessage = categoriesState.error.toString())

        is ResourceState.Success -> CategoriesSuccessState(categories = categoriesState.data,
                                                           subCategoriesState = uiState.subCategories,
                                                           selectedIndex = uiState.selectedIndex,
                                                           padding = padding,
                                                           onCategorySelected = { index, category ->
                                                               categoryViewModel.onIntent(CategoryContract.Intent.GetSubCategoriesWithSelectedCategory(category = category,
                                                                                                                                                       index = index))
                                                           },
                                                           onSubCategoryClick = { id ->
                                                               categoryViewModel.onIntent(CategoryContract.Intent.OnSubCategoryClick(id))
                                                           },
                                                           onRefresh = {
                                                               categoryViewModel.onIntent(CategoryContract.Intent.RefreshCategories)
                                                           },
                                                           onRetry = {
                                                               categoryViewModel.onIntent(CategoryContract.Intent.RetryLastOperation)
                                                           })

        else -> {}
    }
}

@Composable
private fun CategoriesLoadingState(padding: PaddingValues) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(padding),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CategoriesErrorState(errorMessage: String) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = "Error: $errorMessage",
             color = Color.Red,
             textAlign = TextAlign.Center,
             fontSize = 18.sp)
    }
}

@Composable
private fun CategoriesSuccessState(
    categories: List<Category>,
    subCategoriesState: ResourceState<List<SubCategory>>,
    selectedIndex: Int,
    padding: PaddingValues,
    onCategorySelected: (Int, Category) -> Unit,
    onSubCategoryClick: (Long) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    Row {
        CategoryNavigationRail(
            categories = categories,
            selectedIndex = selectedIndex,
            onCategorySelected = onCategorySelected,
        )

        Spacer(modifier = Modifier.width(10.dp))

        SubCategoryGrid(
            subCategoriesState = subCategoriesState,
            padding = padding,
            onSubCategoryClick = onSubCategoryClick,
            onRefresh = onRefresh,
            onRetry = onRetry,
        )
    }
}













