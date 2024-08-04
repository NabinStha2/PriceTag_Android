package com.example.price_tag.ui.features.home

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class CarouselItem(val imageResId: Int)

data class ProductItem(
    val name: String,
    val price: String,
    val categoryName: String,
    val imageResId: Int,
    val isFav: Boolean,
)

@OptIn(ExperimentalFoundationApi::class)
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

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(padding)
            .wrapContentHeight()
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))

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

        Spacer(modifier = Modifier.padding(top = 10.dp))

        CarouselSlider(
            images = images,
            pageCount = images.size,
            pagerState = pagerState,
            coroutine = coroutineScope,
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
        ) {
            items(count = productList.size) { index ->
                ProductCardView(index = index, productItem = productList[index])
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCardView(index: Int, productItem: ProductItem?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(12.dp)),
        onClick = {},
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = productItem?.imageResId!!),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = productItem.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = productItem.categoryName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = productItem.price,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSlider(
    images: List<CarouselItem>,
    pageCount: Int,
    pagerState: PagerState,
    coroutine: CoroutineScope,
) {
    Box(
        modifier = Modifier
            .height(230.dp)
            .fillMaxWidth(),
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            verticalAlignment = Alignment.CenterVertically,
            pageSize = PageSize.Fill,
            userScrollEnabled = true,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.9f else 1f,
                label = "Image size animation",
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
            )

            LaunchedEffect(key1 = pagerState.currentPage) {
                delay(3000)
                coroutine.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % pagerState.pageCount,
                        animationSpec = tween(durationMillis = 300, easing = EaseIn),
                    )
                }
            }

            Image(
                painter = painterResource(id = images[page].imageResId),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    },
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .absoluteOffset(y = (-10).dp),
        ) {
            repeat(images.size) { pageValue ->
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .height(5.dp)
                        .width(5.dp)
                        .clip(CircleShape)
                        .background(color = if (pagerState.currentPage == pageValue) Color.Blue else Color.White),
                ) {}
                Spacer(modifier = Modifier.width(5.dp))
            }
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
    ) { HomeView(padding) }
}