package com.example.price_tag.ui.features.productDetails.components

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
import com.example.price_tag.domain.model.product.ProductDetailsRatingReviews
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.utilites.ResourceState

@Composable
fun ProductDetailsReviewContent(
    productDetails: SingleProductDetails,
    productDetailsRatingReviews: ResourceState<ProductDetailsRatingReviews>,
) {

    when (productDetailsRatingReviews) {
        is ResourceState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is ResourceState.Error -> {
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

        is ResourceState.Success -> {
            val ratingReviews: ProductDetailsRatingReviews = productDetailsRatingReviews.data

            ratingReviews.productDetailsRatingReviewsData.forEach { ratingReviewData ->
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
                                Text(text = ratingReviewData.userName)
                                Text(text = "⭐".repeat(ratingReviewData.rating.toInt()))
                            }
                        }
                        Text(
                            text = ratingReviewData.reviews,
                            fontStyle = FontStyle.Italic,
                        )

                    }
                }
            }

        }

        else -> {}
    }
}
