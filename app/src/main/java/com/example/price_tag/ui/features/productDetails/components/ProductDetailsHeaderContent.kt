package com.example.price_tag.ui.features.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.PrimaryColor50
import com.example.price_tag.core.theme.PrimaryColor600
import com.example.price_tag.core.theme.PrimaryColor700
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.price_tag.ui.components.StarRating

@Composable
fun ProductDetailsHeaderContent(
    productDetailsData: SingleProductDetails,
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                StarRating(rating = productDetailsData.totalRating)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = productDetailsData.totalRating.toString(),
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "(${productDetailsData.totalReview} Reviews)",
                    fontSize = 12.sp,
                )
            }

            Text(
                text = productDetailsData.brand ?: "",
                fontSize = 16.sp,
                color = PrimaryColor700,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Au$${productDetailsData.discountedPrice}",
                fontSize = 18.sp,
                color = PrimaryColor600,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Au$${productDetailsData.basePrice}",
                fontSize = 14.sp,
                textDecoration = TextDecoration.LineThrough,
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = productDetailsData.name ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(36.dp)
                        .background(color = PrimaryColor50),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = PrimaryColor600,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(36.dp)
                        .background(color = PrimaryColor50),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share",
                        tint = PrimaryColor600,
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = productDetailsData.shortDescription ?: "Short Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(color = NeutralColor200)

        Spacer(modifier = Modifier.height(10.dp))
    }
}