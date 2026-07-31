package com.example.price_tag.feature.product.presentation.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.R
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviews
import com.example.price_tag.feature.product.domain.model.ProductDetailsRatingReviewsData
import com.example.utilites.ResourceState

@Composable
fun ProductDetailsReviewContent(
    productDetailsRatingReviews: ResourceState<ProductDetailsRatingReviews>,
    reviewListData: List<ProductDetailsRatingReviewsData> = emptyList(),
    showAll: Boolean = false,
) {
    when (productDetailsRatingReviews) {
        is ResourceState.Loading -> {
            if (reviewListData.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is ResourceState.Error -> {
            if (reviewListData.isEmpty()) {
                val error = productDetailsRatingReviews.error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(),
                ) {
                    Text(text = error,
                         color = Color.Red,
                         fontSize = 18.sp)
                }
            }
        }

        is ResourceState.Success -> {
            val listToDisplay = if (showAll) {
                reviewListData.ifEmpty { productDetailsRatingReviews.data.productDetailsRatingReviewsData }
            } else {
                productDetailsRatingReviews.data.productDetailsRatingReviewsData.take(3)
            }

            Column {
                listToDisplay.forEach { ratingReviewData ->
                    RatingReviewCard(
                        productDetailsRatingReviewsData = ratingReviewData,
                    )
                }
            }
        }

        else -> {}
    }
}

@Composable
fun RatingReviewCard(
    productDetailsRatingReviewsData: ProductDetailsRatingReviewsData,
) {
    Box(modifier = Modifier.padding(bottom = 8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = NeutralColor200,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
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
                    Text(text = productDetailsRatingReviewsData.userName)
                    Text(text = "⭐".repeat(productDetailsRatingReviewsData.rating.toInt()))
                }
            }
            Text(
                text = productDetailsRatingReviewsData.reviews,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}
