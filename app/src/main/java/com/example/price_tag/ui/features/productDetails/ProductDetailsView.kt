package com.example.price_tag.ui.features.productDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.price_tag.R
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.NeutralColor800
import com.example.price_tag.core.theme.Poppins
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.domain.model.product.ColorSizeVariantGroup
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.price_tag.ui.components.CartIconWithBadge
import com.example.price_tag.ui.features.home.CarouselItem
import com.example.price_tag.ui.features.home.widgets.CarouselSlider
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsDescriptionContent
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsHeaderContent
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsReviewContent
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsSelectColor
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsSelectSize
import com.example.utilites.ResourceState

@OptIn(ExperimentalMaterial3Api::class,
       ExperimentalFoundationApi::class)
@Composable
fun ProductDetailsView(
    navController: NavController,
    paddingValues: PaddingValues,
    productDetailsViewModel: ProductDetailsViewModel,
    productId: Long?,
) {
    val images = listOf(
        CarouselItem(R.drawable.carousel1),
        CarouselItem(R.drawable.carousel2),
        CarouselItem(R.drawable.carousel3),
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(
        "Description",
        "Reviews",
    )

    val productDetailsState: ProductDetailsContract.ProductDetailsState =
        productDetailsViewModel.productDetailsState.collectAsStateWithLifecycle().value

    LaunchedEffect(true) {
        if (productId != null) {
            productDetailsViewModel.onIntent(
                ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetails(productId = productId),
            )
        }
    }

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1 && productId != null && productDetailsState.getSingleProductDetailsData is ResourceState.Success) {
            productDetailsViewModel.onIntent(ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetailsRatingReviews(productId))
        }
    }

    Scaffold(modifier = Modifier
        .padding(horizontal = 16.dp)
        .systemBarsPadding(),
             topBar = {
                 CenterAlignedTopAppBar(navigationIcon = {
                     IconButton(onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .border(shape = CircleShape.copy(CornerSize(percent = 100)),
                                            border = BorderStroke(width = 1.dp,
                                                                  color = NeutralColor200))
                                    .size(35.dp)) {
                         Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                              contentDescription = "Back")
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
                                        })
             },

             bottomBar = {
                 BottomAppBar(
                     modifier = Modifier
                         .height(60.dp)
                         .fillMaxWidth(),
                     containerColor = Color.Transparent,
                 ) {
                     Row(modifier = Modifier.fillMaxWidth(),
                         horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                         Button(
                             onClick = { /*TODO*/ },
                             modifier = Modifier.weight(1f),
                             colors = ButtonColors(
                                 containerColor = PrimaryColor,
                                 contentColor = Color.White,
                                 disabledContentColor = NeutralColor800,
                                 disabledContainerColor = NeutralColor100,
                             ),
                             enabled = (productDetailsState.selectedVariant?.stockQuantity
                                        ?: 0) > 0,
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
                             ),
                             enabled = (productDetailsState.selectedVariant?.stockQuantity
                                        ?: 0) > 0,
                         ) {
                             Text(text = "Add to Cart")
                         }

                     }
                 }
             }

    ) { innerPadding ->

        when (productDetailsState.getSingleProductDetailsData) {
            is ResourceState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
                }
            }

            is ResourceState.Success -> {
                val productDetails: SingleProductDetails =
                    productDetailsState.getSingleProductDetailsData.data
                val colorSizeVariantGroup: List<ColorSizeVariantGroup> =
                    productDetails.colorSizeVariantGroup ?: emptyList()

                LazyColumn(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                           verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        CarouselSlider(
                            images = images,
                            pageCount = images.size,
                            pagerState = pagerState,
                            coroutine = coroutineScope,
                        )
                    }

                    item { Spacer(modifier = Modifier.height(10.dp)) }

                    item {
                        ProductDetailsHeaderContent(
                            productDetailsData = productDetails,
                        )
                    }

                    item {
                        ProductDetailsSelectColor(
                            colorSizeVariantGroup = colorSizeVariantGroup,
                            productDetailsState = productDetailsState,
                            productDetailsViewModel = productDetailsViewModel,
                        )
                    }

                    item { Spacer(modifier = Modifier.height(10.dp)) }


                    if (productDetailsState.selectedColor?.isNotEmpty() == true) item {
                        ProductDetailsSelectSize(
                            colorSizeVariantGroup = colorSizeVariantGroup,
                            productDetailsState = productDetailsState,
                            productDetailsViewModel = productDetailsViewModel,
                        )
                    }

                    item {
                        productDetailsState.selectedVariant?.let {
                            if (it.stockQuantity > 0) Text(
                                "In Stock: ${it.stockQuantity}",
                                fontSize = 14.sp,
                                color = Color.Green,
                            )
                            else Text(
                                "Out of Stock",
                                fontSize = 14.sp,
                                color = Color.Red,
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(15.dp)) }

                    stickyHeader { // Tab Row at the top
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier.fillMaxWidth(),
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
                                    modifier = Modifier //                                .size(40.dp)
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .background(color = if (selectedTabIndex == index) PrimaryColor else Color.Transparent),
                                )
                            }
                        }
                    }

                    item {
                        when (selectedTabIndex) {
                            0 -> ProductDetailsDescriptionContent(productDetails = productDetails)
                            1 -> ProductDetailsReviewContent(
                                productDetails = productDetails,
                                productDetailsRatingReviews = productDetailsState.getSingleProductDetailsRatingReviews,
                            )
                        }
                    }

                }
            }

            is ResourceState.Error -> {
                val error = productDetailsState.getSingleProductDetailsData.error
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                    Text(text = error,
                         color = Color.Red,
                         fontSize = 18.sp)
                }
            }

            else -> {}
        }
    }

}























