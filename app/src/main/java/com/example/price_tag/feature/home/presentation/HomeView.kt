package com.example.price_tag.feature.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.R
import com.example.price_tag.core.ui.components.CartIconWithBadge
import com.example.price_tag.core.ui.viewmodel.SelectedCategoryViewModel
import com.example.price_tag.feature.cart.presentation.CartViewModel
import com.example.price_tag.feature.home.presentation.widgets.CarouselSlider
import com.example.utilites.ResourceState

data class CarouselItem(val imageResId: Int)

data class ProductItem(
    val name: String,
    val price: String,
    val categoryName: String,
    val imageResId: Int,
    val isFav: Boolean,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeView(
    navController: NavHostController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val images = listOf(
        CarouselItem(R.drawable.carousel1),
        CarouselItem(R.drawable.carousel2),
        CarouselItem(R.drawable.carousel3),
    )
    val productList = listOf(
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()
    val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .height(56.dp)
                        .weight(5f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                        }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .height(56.dp)
                                .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)),
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

                val cartCount =
                    (cartState.getAllCartItems as? ResourceState.Success)?.data?.totalItemCount?.toInt()
                        ?: 0
                CartIconWithBadge(count = cartCount) {
                    viewModel.onCartClick()
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            CarouselSlider(
                images = images,
                pageCount = images.size,
                pagerState = pagerState,
                coroutine = coroutineScope,
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Featured Products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                TextButton(onClick = { viewModel.onSeeAllFeaturedClick() }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "See all", fontSize = 14.sp, fontWeight = FontWeight.W400
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Arrow Forward",
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Popular Products",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                TextButton(onClick = { }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "See all", fontSize = 14.sp, fontWeight = FontWeight.W400
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Arrow Forward",
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(
                modifier = Modifier
                    .height(250.dp)
                    .wrapContentWidth(),
            ) {
                items(productList.size) { index ->
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

    }
}
