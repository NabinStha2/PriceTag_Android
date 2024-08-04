package com.example.price_tag.ui.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.R
import com.example.price_tag.ui.features.home.widgets.CarouselSlider
import com.example.price_tag.ui.features.home.widgets.ProductCard
import com.example.price_tag.ui.features.main.MainView


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
fun HomeView(padding: PaddingValues = PaddingValues(0.dp)) {
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

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(padding)
            .padding(top = 8.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
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
                            // Handle search action here
                            // e.g., perform a search, close the keyboard, etc.
                        }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = { /*TODO*/ },
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

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .border(
                            1.dp,
                            Color.White,
                            RoundedCornerShape(12.dp),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Search Icon",
                        tint = Color.White,
                    )
                }
            }
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            CarouselSlider(
                images = images,
                pageCount = images.size,
                pagerState = pagerState,
                coroutine = coroutineScope,
            )
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
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
                TextButton(onClick = { /*TODO*/ }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "See all", fontSize = 14.sp, fontWeight = FontWeight.W400
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Arrow Forward",
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
            }
        }

        itemsIndexed(items = productList) { index, product ->
            ProductCard(index = index, productItem = product)
        }
    }
}


@Preview
@Composable
fun HomeViewPreview(padding: PaddingValues = PaddingValues(16.dp)) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = Color.White)
    ) { MainView() }
}