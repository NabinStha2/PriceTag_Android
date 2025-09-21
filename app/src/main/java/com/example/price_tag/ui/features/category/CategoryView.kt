package com.example.price_tag.ui.features.category

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.R
import com.example.price_tag.data.model.category.CategoryResponse
import com.example.price_tag.data.model.category.CategoryResult
import com.example.price_tag.data.model.category.SubCategoryResponse
import com.example.price_tag.data.model.category.SubCategoryResult
import com.example.price_tag.ui.components.SharedViewModel
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.utilites.ResourceState

@Composable
fun CategoryView(
    navController: NavHostController = rememberNavController(),
    padding: PaddingValues,
    categoryViewModel: CategoryViewModel,
    sharedViewModel: SharedViewModel,
) {
    val TAG = "CategoryView"
    val configuration = LocalConfiguration.current
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    var selectedCategoryId by rememberSaveable { mutableStateOf<Long?>(null) } // Store the selected category ID
    val spUtils = categoryViewModel.spUtils
    val getAllCategoriesData by categoryViewModel.getAllCategoriesData.collectAsStateWithLifecycle()
    val getSubCategoriesData by categoryViewModel.getSubCategoriesData.collectAsStateWithLifecycle()


    when (getAllCategoriesData) {
        is ResourceState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }

        is ResourceState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Error: ${(getAllCategoriesData as ResourceState.Error<CategoryResponse>).error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }

        is ResourceState.Success -> {
            val categoryResponseData: CategoryResponse =
                (getAllCategoriesData as ResourceState.Success<CategoryResponse>).data

            // Load subcategories for the first category by default
            LaunchedEffect(categoryResponseData) {
                if (selectedCategoryId != null) {
                    categoryViewModel.getSubCategoriesWithSelectedCategory(
                        categoryId = selectedCategoryId ?: 0
                    )

                } else {
                    val firstCategoryId = categoryResponseData.data?.results?.firstOrNull()?.id
                    if (firstCategoryId != null) {
                        selectedCategoryId = firstCategoryId
                        categoryViewModel.getSubCategoriesWithSelectedCategory(categoryId = firstCategoryId)
                    }

                }
            }
            LaunchedEffect(selectedItem) {
                val categoryName =
                    categoryResponseData.data?.results?.getOrNull(selectedItem)?.categoryName.orEmpty()
                sharedViewModel.setCategoryName(categoryName)
            }

            Log.i(
                TAG,
                "🔹 Selected Category: ${sharedViewModel.selectedCategoryName.collectAsStateWithLifecycle().value}"
            )
            Row {
                /** 🔹 Category Navigation Rail (Only Updates Selected State) **/
                CategoryNavigationRail(
                    categories = categoryResponseData.data?.results.orEmpty(),
                    selectedItem = selectedItem,
                    onCategorySelected = { index, categoryId ->
                        selectedItem = index
                        selectedCategoryId = categoryId
                        categoryViewModel.getSubCategoriesWithSelectedCategory(categoryId)
                    },
                    padding = padding
                )

                Spacer(modifier = Modifier.width(10.dp))

                /** 🔹 Subcategory Grid (Rebuilds Only When Subcategories Change) **/
                SubCategoryGrid(
                    getSubCategoriesData = getSubCategoriesData,
                    navController = navController,
                    padding = padding
                )
            }
        }

        else -> {}
    }
}

/** 🔹 Category Navigation Rail (Only Updates State) **/
@Composable
fun CategoryNavigationRail(
    categories: List<CategoryResult>,
    selectedItem: Int,
    onCategorySelected: (Int, Long) -> Unit,
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
            val isSelected = selectedItem == index

            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                NavigationRailItem(
                    selected = isSelected,
                    onClick = { onCategorySelected(index, item.id ?: 0) },
                    icon = {
                        Text(
                            text = item.categoryName ?: "",
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth(),
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
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

/** 🔹 Subcategory Grid (Recomposes Only When Subcategories Change) **/
@Composable
fun SubCategoryGrid(
    getSubCategoriesData: ResourceState<SubCategoryResponse>,
    navController: NavHostController,
    padding: PaddingValues
) {
    when (getSubCategoriesData) {
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
                    text = "Error: ${(getSubCategoriesData as ResourceState.Error<SubCategoryResponse>).error}",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }

        is ResourceState.Success -> {
            val subCategoryResponseData = getSubCategoriesData.data
            val gridState = rememberSaveable(saver = LazyGridState.Saver) {
                LazyGridState()
            }
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(subCategoryResponseData.data?.results.orEmpty().size) { index ->
                    val subCategory = subCategoryResponseData.data?.results?.get(index)
                    SubCategoryItem(subCategory, navController)
                }
            }
        }

        else -> {}
    }
}

/** 🔹 Subcategory Item UI **/
@Composable
fun SubCategoryItem(subCategory: SubCategoryResult?, navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                navController.navigate(NavigationItem.ProductView.createRoute(subCategoryId = subCategory?.id)) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(NavigationItem.MainView.route) {
                        saveState = true
                    }
                }
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
                text = subCategory?.subCategoryName ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


//@Preview
//@Composable
//fun CategoryViewPreview() {
//    MainView()
//}