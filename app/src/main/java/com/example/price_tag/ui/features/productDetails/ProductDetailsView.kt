package com.example.price_tag.ui.features.productDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.R
import com.example.price_tag.ui.components.CartIconWithBadge
import com.example.price_tag.ui.components.StarRating
import com.example.price_tag.ui.features.home.CarouselItem
import com.example.price_tag.ui.features.home.widgets.CarouselSlider
import com.example.price_tag.ui.theme.NeutralColor100
import com.example.price_tag.ui.theme.NeutralColor200
import com.example.price_tag.ui.theme.NeutralColor800
import com.example.price_tag.ui.theme.Poppins
import com.example.price_tag.ui.theme.PrimaryColor
import com.example.price_tag.ui.theme.PrimaryColor50
import com.example.price_tag.ui.theme.PrimaryColor600
import com.example.price_tag.ui.theme.WarningColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun ProductDetailsView(
//    navController: NavController = rememberNavController(),
//    paddingValues: PaddingValues,
//    productViewModel: ProductViewModel,
//    sharedViewModel: SharedViewModel,
//    productId: Long?,
//    productName: String?,
) {
    val images = listOf(
        CarouselItem(R.drawable.carousel1),
        CarouselItem(R.drawable.carousel2),
        CarouselItem(R.drawable.carousel3),
    )
    val productSelectSize = listOf("S", "M", "L", "XL")
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()
    var productSize by rememberSaveable {
        mutableStateOf("")
    }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Description", "Reviews")

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .border(
                                shape = CircleShape.copy(CornerSize(percent = 100)),
                                border = BorderStroke(width = 1.dp, color = NeutralColor200)
                            )
                            .size(35.dp)

                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Product Details",
                        fontFamily = Poppins,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                actions = {
                    CartIconWithBadge()
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                containerColor = Color.Transparent,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonColors(
                            containerColor = PrimaryColor,
                            contentColor = Color.White,
                            disabledContentColor = NeutralColor800,
                            disabledContainerColor = NeutralColor100,
                        )
                    ) {
                        Text(text = "Buy Now")
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonColors(
                            containerColor = PrimaryColor,
                            contentColor = Color.White,
                            disabledContentColor = NeutralColor800,
                            disabledContainerColor = NeutralColor100,
                        )
                    ) {
                        Text(text = "Add to Cart")
                    }

                }
            }
        }

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                CarouselSlider(
                    images = images,
                    pageCount = images.size,
                    pagerState = pagerState,
                    coroutine = coroutineScope,
                )
            }

            item {
                Row {
                    StarRating(rating = 4)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "20 Reviews", fontSize = 12.sp)
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Product Name", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(36.dp)
                                .background(color = PrimaryColor50)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = PrimaryColor600
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        IconButton(
                            onClick = { /*TODO*/ }, modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(36.dp)
                                .background(color = PrimaryColor50)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "Share",
                                tint = PrimaryColor600
                            )
                        }

                    }
                }
            }

            item {
                Text(text = "Short Description", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                HorizontalDivider(color = NeutralColor200)
            }

            item {
                Text(text = "Select Size", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    productSelectSize.forEachIndexed { _, item ->
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(percent = 15))
                                .clickable {
                                    productSize = item
                                }
                                .background(color = if (productSize == item) PrimaryColor else Color.Transparent)
                                .border(
                                    shape = RoundedCornerShape(percent = 15),
                                    width = 1.dp,
                                    color = NeutralColor100
                                )
                                .size(30.dp),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(
                                text = item,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (productSize == item) Color.White else NeutralColor800
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            item {
                Text(text = "Select Color", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    for (i in 1..5) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(WarningColor)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

            }

            item { Spacer(modifier = Modifier.height(10.dp)) }

            stickyHeader {
                // Tab Row at the top
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .fillMaxWidth(),
                    indicator = {},
                    divider = {},
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) },
                            selectedContentColor = Color.White,
                            unselectedContentColor = NeutralColor800,
                            modifier = Modifier
//                                .size(40.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(color = if (selectedTabIndex == index) PrimaryColor else Color.Transparent)
                        )
                    }
                }
            }

            item {
                when (selectedTabIndex) {
                    0 -> DescriptionContent()
                    1 -> ReviewContent()
                }
            }

        }

    }

}

@Composable
fun DescriptionContent() {
    Text(
        text = "This is the product description. It explains the product details, features, and specifications.",
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ReviewContent() {
    for (i in 1..8) {
        Box(modifier = Modifier.padding(bottom = 8.dp)) {
            Column(
                modifier = Modifier
                    .border(width = 1.dp, color = NeutralColor200, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carousel1),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = "User Name")
                        Text(text = "⭐️⭐️⭐️⭐️️")
                    }
                }
                Text(text = "This is the product review. It explains the product details, features, and specifications.")

            }
        }
    }
}



















