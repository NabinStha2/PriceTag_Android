package com.example.price_tag.ui.features.rating_review

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.Poppins
import com.example.price_tag.ui.features.productDetails.ProductDetailsContract
import com.example.price_tag.ui.features.productDetails.ProductDetailsViewModel
import com.example.price_tag.ui.features.productDetails.components.ProductDetailsReviewContent
import com.example.utilites.ResourceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingReviewView(
    productId: Long?,
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel,
) {
    val productDetailsState: ProductDetailsContract.ProductDetailsState =
        productDetailsViewModel.productDetailsState.collectAsStateWithLifecycle().value

    LaunchedEffect(true) {
        if (productId != null) {
            productDetailsViewModel.onIntent(ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetailsRatingReviews(productId))
        }
    }


    Scaffold(
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {
                    Text(
                        text = "Reviews",
                        fontFamily = Poppins,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
            )
        },
    ) { innerPadding ->
        when (productDetailsState.getSingleProductDetailsRatingReviews) {
            is ResourceState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp,
                    ),
                ) {
                    item {
                        ProductDetailsReviewContent(
                            productDetailsRatingReviews = productDetailsState.getSingleProductDetailsRatingReviews,
                            showAll = true,
                        )
                    }
                }
            }

            is ResourceState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResourceState.Error -> {
                val error = productDetailsState.getSingleProductDetailsRatingReviews.error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                ) {
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 18.sp,
                    )
                }
            }

            else -> {}
        }
    }
}


//@Composable
//@Preview
//fun RatingReviewViewPreview{
//
//}