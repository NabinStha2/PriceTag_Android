package com.example.price_tag.ui.features.product

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.PrimaryColor300
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.home.widgets.ProductCard
import com.example.utilites.ResourceState


@OptIn(ExperimentalFoundationApi::class,
       ExperimentalMaterial3Api::class)
@Composable
fun ProductView(
    navController: NavController = rememberNavController(),
    subCategoryId: Long?,
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val focusManager = LocalFocusManager.current

    val productState: ProductContract.ProductState by productViewModel.productState.collectAsStateWithLifecycle()

    val categoryName by selectedCategoryViewModel.selectedCategoryName.collectAsStateWithLifecycle()
    var productSearchValue by rememberSaveable {
        mutableStateOf("")
    }
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        productViewModel.effect.collect { effect ->
            when (effect) {
                is ProductContract.ProductEffect.NavigateToProductDetails -> {
                    navController.navigate(NavigationItem.ProductDetailsView(effect.productId))
                }
            }
        }
    }

    LaunchedEffect(
        lazyGridState,
    ) {
        snapshotFlow {
            lazyGridState.canScrollForward
        }.collect { canScrollForward ->
            Log.e("ProductView",
                  "canScrollForward: $canScrollForward page: ${productState.currentPage} lastPage: ${productState.isLastPage} isLoadingMore: ${productState.isLoadingMore}")
            if (!canScrollForward && !productState.isLoadingMore && !productState.isLastPage) {
                if (productSearchValue.isEmpty()) {
                    subCategoryId?.let {
                        productViewModel.onIntent(
                            ProductContract.ProductIntent.GetAllSubCategoryProducts(
                                subCategoryId = it,
                                page = productState.currentPage + 1,
                                limit = 10,
                                order = "asc",
                            ),
                        )

                    }
                } else {
                    productViewModel.onIntent(
                        ProductContract.ProductIntent.GetAllSubCategorySearchProducts(
                            subCategoryId = subCategoryId!!,
                            searchQuery = productSearchValue,
                            page = productState.currentPage + 1,
                            limit = 10,
                        ),
                    )
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier.background(
            PrimaryColor300,
        ),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .border(
                                shape = CircleShape.copy(CornerSize(percent = 100)),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = NeutralColor200,
                                ),
                            )
                            .size(35.dp),
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                             contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = "Products",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
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
                        Log.i("ProductView",
                              "onSearch: $productSearchValue and ${productState.isLoadingMore}")
                        if (!productState.isLoadingMore) {
                            productViewModel.onIntent(
                                ProductContract.ProductIntent.GetAllSubCategorySearchProducts(
                                    subCategoryId = subCategoryId!!,
                                    searchQuery = productSearchValue,
                                    page = 1,
                                    limit = 10,
                                ),
                            )
                        }
                    }),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                if (!productState.isLoadingMore) {
                                    productViewModel.onIntent(
                                        ProductContract.ProductIntent.GetAllSubCategorySearchProducts(
                                            subCategoryId = subCategoryId!!,
                                            searchQuery = productSearchValue,
                                            page = 1,
                                            limit = 10,
                                        ),
                                    )
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

            when (productState.getAllSubCategoryProductsData) {
                is ResourceState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                    }
                }

                is ResourceState.Error -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        Text(
                            text = "Error: ${(productState.getAllSubCategoryProductsData as ResourceState.Error).error}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.error,
                            ),
                        )
                    }
                }

                is ResourceState.Success -> {
                    if (productState.productListData.isNotEmpty()) {
                        LazyVerticalGrid(
                            state = lazyGridState,
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                            ),
                        ) {
                            itemsIndexed(items = productState.productListData) { index, product ->
                                ProductCard(
                                    index = index,
                                    product = product,
                                    categoryName = categoryName,
                                    onClick = {
                                        productViewModel.onIntent(ProductContract.ProductIntent.OnProductClicked(product.id))
                                    },
                                )
                            }

                            item {
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp))
                            }

                            // Show loading indicator at the bottom
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                if (productState.isLoadingMore) {
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
