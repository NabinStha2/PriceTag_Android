package com.example.price_tag.ui.features.rating_review

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.price_tag.ui.features.productDetails.components.RatingReviewCard
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

    val lazyListState = rememberLazyListState()

    val isNearBottom by remember {
        derivedStateOf {
            val totalItemsCount = lazyListState.layoutInfo.totalItemsCount
            Log.d("IsNearBottom",
                  "Total items count: $totalItemsCount")
            val lastVisibleItemIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            Log.d("IsNearBottom",
                  "Last visible item index: $lastVisibleItemIndex")
            totalItemsCount > 0 && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(isNearBottom,
                   productDetailsState.isLastReviewPage,
                   productDetailsState.isLoadingMoreReviews) {
        Log.i("Outside RatingReviewView",
              "isNearBottom: $isNearBottom, isLastReviewPage: ${productDetailsState.isLastReviewPage}, isLoadingMoreReviews: ${productDetailsState.isLoadingMoreReviews}")
        if (isNearBottom && productId != null && productDetailsState.reviewListData.isNotEmpty() && !productDetailsState.isLastReviewPage && !productDetailsState.isLoadingMoreReviews) {
            Log.d("Inside RatingReviewView",
                  "isNearBottom: $isNearBottom, isLastReviewPage: ${productDetailsState.isLastReviewPage}, isLoadingMoreReviews: ${productDetailsState.isLoadingMoreReviews}")
            productDetailsViewModel.onIntent(
                ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetailsRatingReviews(
                    productId = productId,
                    page = productDetailsState.currentReviewPage + 1,
                ),
            )
        }
    }

    LaunchedEffect(true) {
        if (productId != null && productDetailsState.reviewListData.isEmpty()) {
            productDetailsViewModel.onIntent(
                ProductDetailsContract.ProductDetailsIntent.GetSingleProductDetailsRatingReviews(
                    productId = productId,
                    page = 1,
                ),
            )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(productDetailsState.reviewListData) { ratingReviewData ->
                    RatingReviewCard(productDetailsRatingReviewsData = ratingReviewData)
                }

                if (productDetailsState.isLoadingMoreReviews) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            if (productDetailsState.reviewListData.isEmpty() && productDetailsState.getSingleProductDetailsRatingReviews is ResourceState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (productDetailsState.reviewListData.isEmpty() && productDetailsState.getSingleProductDetailsRatingReviews is ResourceState.Error) {
                val error = productDetailsState.getSingleProductDetailsRatingReviews.error
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}