package com.example.price_tag.ui.features.productDetails.components

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
import com.example.price_tag.core.theme.NeutralColor400
import com.example.price_tag.core.theme.NeutralColor50
import com.example.price_tag.domain.model.product.SingleProductDetails

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
            .border(
                color = MaterialTheme.colorScheme.background,
                width = 1.dp,
                shape = RoundedCornerShape(percent = 20),
            )
            .background(
                color = MaterialTheme.colorScheme.background,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "The Description",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = if (readMore.value) productDetails.description
                                           ?: "" else productDetails.description?.take(80) ?: "",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(15.dp))
            if ((productDetails.description?.length ?: 0) > 50) {
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
}