package com.example.price_tag.ui.features.product

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.home.widgets.ProductCard
import com.example.utilites.ResourceState


@OptIn(ExperimentalFoundationApi::class,
       ExperimentalMaterial3Api::class)
@Composable
fun ProductView(
    navController: NavController = rememberNavController(),
    subCategoryId: Long?,
    subCategoryName: String? = "",
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val focusManager = LocalFocusManager.current

    val categoryName by selectedCategoryViewModel.selectedCategoryName.collectAsStateWithLifecycle()
    var productSearchValue by rememberSaveable {
        mutableStateOf("")
    }
    val getAllSubCategoryProducts by productViewModel.getAllSubCategoryProductsData.collectAsStateWithLifecycle()
    val productsList = productViewModel.productsList
    val isLoadingMore by productViewModel.isLoadingMore
    val isLastPage by productViewModel.isLastPage
    val currentPage by productViewModel.currentPage
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            lazyGridState.canScrollForward
        }.collect { canScrollForward ->
            Log.e("ProductView",
                  "canScrollForward: $canScrollForward page: $currentPage lastPage: $isLastPage isLoadingMore: $isLoadingMore")
            if (!canScrollForward && !isLoadingMore && !isLastPage) {
                if (productSearchValue.isEmpty()) {
                    subCategoryId?.let {
                        productViewModel.getAllSubCategoryProducts(
                            subCategoryId = it,
                            page = productViewModel.currentPage.intValue,
                            limit = 5,
                        )
                    }
                } else {
                    productViewModel.getAllSubCategorySearchProductsData(
                        subCategoryId = subCategoryId!!,
                        page = productViewModel.currentPage.intValue,
                        limit = 5,
                        name = productSearchValue,
                    )
                }
            }
        }
    }


    Scaffold(modifier = Modifier.padding(horizontal = 16.dp),
             topBar = {
                 TopAppBar(title = {
                     Text(
                         text = "Products",
                         fontSize = 22.sp,
                         fontWeight = FontWeight.W800,
                     )
                 },
                           navigationIcon = {
                               IconButton(onClick = { navController.popBackStack() }) {
                                   Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back")
                               }
                           })
             }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Row {
                OutlinedTextField(
                    value = productSearchValue,
                    onValueChange = { value ->
                        productSearchValue = value
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(6f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        if (!isLoadingMore) {
                            productViewModel.getAllSubCategorySearchProductsData(subCategoryId = subCategoryId!!,
                                                                                 page = 1,
                                                                                 limit = 5,
                                                                                 name = productSearchValue)
                        }
                    }),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                if (!isLoadingMore) {
                                    productViewModel.getAllSubCategorySearchProductsData(subCategoryId = subCategoryId!!,
                                                                                         page = 1,
                                                                                         limit = 5,
                                                                                         name = productSearchValue)
                                }
                            },
                            modifier = Modifier
                                .height(56.dp)
                                .clip(RoundedCornerShape(topEnd = 12.dp,
                                                         bottomEnd = 12.dp)),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Black,
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            Color.White,
                            RoundedCornerShape(12.dp),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Search Icon",
                        tint = Color.Black,
                    )
                }
            }

            when (getAllSubCategoryProducts) {
                is ResourceState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                    }
                }

                is ResourceState.Error -> {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Error: ${(getAllSubCategoryProducts as ResourceState.Error).error}",
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                        )
                    }
                }

                is ResourceState.Success -> {
                    if (productsList.isNotEmpty()) {
                        LazyVerticalGrid(state = lazyGridState,
                                         columns = GridCells.Fixed(2),
                                         verticalArrangement = Arrangement.spacedBy(12.dp),
                                         horizontalArrangement = Arrangement.spacedBy(12.dp),
                                         modifier = Modifier
                                             .fillMaxSize()
                                             .background(Color.White)) {
                            itemsIndexed(items = productsList) { index, product ->
                                ProductCard(index = index,
                                            product = product,
                                            categoryName = categoryName,
                                            onClick = {
                                                productViewModel.onProductClick(product.id)
                                            })
                            }

                            item {
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp))
                            }

                            // Show loading indicator at the bottom
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                if (isLoadingMore) {
                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                        contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = "No Products Found",
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }

                else -> {}

            }
        }
    }


}
