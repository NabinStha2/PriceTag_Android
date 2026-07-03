package com.example.price_tag.ui.features.productDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.price_tag.R
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.domain.model.product.SingleProductDetails

@Composable
fun ProductDetailsReviewContent(
    productDetails: SingleProductDetails,
) {
    for (i in 1..8) {
        Box(modifier = Modifier.padding(bottom = 8.dp)) {
            Column(
                modifier = Modifier
                    .border(width = 1.dp,
                            color = NeutralColor200,
                            shape = RoundedCornerShape(8.dp))
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
                        Text(text = "User Name")
                        Text(text = "⭐️⭐️⭐️⭐️️")
                    }
                }
                Text(text = "This is the product review. It explains the product details, features, and specifications.")

            }
        }
    }
}