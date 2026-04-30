package com.example.price_tag.ui.features.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.price_tag.R
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.utilites.ResourceState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryView(
    padding: PaddingValues,
    categoryViewModel: CategoryViewModel,
) {
    val uiState by categoryViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        categoryViewModel.effect.collectLatest { effect ->
            when (effect) {
                is CategoryContract.Effect.NavigateToProducts -> {
                    // Navigation is already handled in ViewModel via Coordinator
                }
            }
        }
    }

    when (val categoriesState = uiState.categories) {
        is ResourceState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResourceState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Error: ${categoriesState.error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }

        is ResourceState.Success -> {
            val categories: List<Category> = categoriesState.data

            Row {
                CategoryNavigationRail(
                    categories = categories,
                    selectedIndex = uiState.selectedIndex,
                    onCategorySelected = { index, category ->
                        categoryViewModel.onIntent(
                            CategoryContract.Intent.GetSubCategoriesWithSelectedCategory(category, index)
                        )
                    },
                    padding = padding,
                )

                Spacer(modifier = Modifier.width(10.dp))

                SubCategoryGrid(
                    subCategoriesState = uiState.subCategories,
                    padding = padding,
                    onSubCategoryClick = { id ->
                        categoryViewModel.onIntent(
                            CategoryContract.Intent.OnSubCategoryClick(id)
                        )
                    },
                )
            }
        }

        else -> {}
    }
}

@Composable
fun CategoryNavigationRail(
    categories: List<Category>,
    selectedIndex: Int,
    onCategorySelected: (Int, Category) -> Unit,
    padding: PaddingValues,
) {
    NavigationRail(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .width(120.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFF8F8F8),
        contentColor = Color.Black,
    ) {
        categories.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                NavigationRailItem(
                    selected = selectedIndex == index,
                    onClick = {
                        onCategorySelected(index, item)
                    },
                    icon = {
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth(),
                            fontSize = 14.sp,
                            fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        indicatorColor = Color(0xFF3F51B5),
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Black,
                    ),
                )
            }
        }
    }
}

@Composable
fun SubCategoryGrid(
    subCategoriesState: ResourceState<List<SubCategory>>,
    padding: PaddingValues,
    onSubCategoryClick: (Long) -> Unit,
) {
    when (subCategoriesState) {
        is ResourceState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResourceState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "Error: ${subCategoriesState.error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }

        is ResourceState.Success -> {
            val subCategories = subCategoriesState.data
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(subCategories.size) { index ->
                    val subCategory = subCategories[index]
                    SubCategoryItem(
                        subCategory,
                        onClick = { id ->
                            onSubCategoryClick(id)
                        },
                    )
                }
            }
        }

        else -> {}
    }
}

@Composable
fun SubCategoryItem(
    subCategory: SubCategory,
    onClick: (Long) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                onClick(subCategory.id)
            },
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.carousel1),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subCategory.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
