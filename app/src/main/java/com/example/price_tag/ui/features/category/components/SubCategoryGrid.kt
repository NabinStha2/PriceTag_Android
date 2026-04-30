package com.example.price_tag.ui.features.category.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.ui.components.EmptyState
import com.example.price_tag.ui.components.ErrorState
import com.example.utilites.ResourceState

@Composable
fun SubCategoryGrid(
    subCategoriesState: ResourceState<List<SubCategory>>,
    padding: PaddingValues,
    onSubCategoryClick: (Long) -> Unit,
    onRefresh: () -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = subCategoriesState is ResourceState.Loading,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize(),
    ) {
        when (subCategoriesState) {
            is ResourceState.Loading -> SubCategoryLoadingState()

            is ResourceState.Error -> ErrorState(
                errorMessage = (subCategoriesState.error as? String) ?: "Unknown error",
                onRetry = onRetry,
            )

            is ResourceState.Success -> {
                if (subCategoriesState.data.isEmpty()) {
                    EmptyState()
                } else {
                    SubCategoryGridContent(
                        subCategories = subCategoriesState.data,
                        padding = padding,
                        onSubCategoryClick = onSubCategoryClick,
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun SubCategoryLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SubCategoryGridContent(
    subCategories: List<SubCategory>,
    padding: PaddingValues,
    onSubCategoryClick: (Long) -> Unit,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(padding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(subCategories.size) { index ->
            SubCategoryItem(
                subCategory = subCategories[index],
                onClick = onSubCategoryClick,
            )
        }
    }
}