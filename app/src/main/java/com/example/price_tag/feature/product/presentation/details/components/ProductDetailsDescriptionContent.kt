package com.example.price_tag.feature.product.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.PrimaryColor100
import com.example.price_tag.feature.product.domain.model.SingleProductDetails

@Composable
fun ProductDetailsDescriptionContent(
    productDetails: SingleProductDetails,
) {
    val readMore = rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(percent = 20))
            .background(
                color = PrimaryColor100,
            )
            .border(
                color = PrimaryColor100,
                width = 1.dp,
                shape = RoundedCornerShape(percent = 20),
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "The Description",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = productDetails.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (!readMore.value) 1 else Int.MAX_VALUE,
            )
            Spacer(modifier = Modifier.height(5.dp))
            TextButton(
                onClick = {
                    readMore.value = !readMore.value
                },
            ) {
                Text(
                    if (readMore.value) "Read less" else "Read more",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}