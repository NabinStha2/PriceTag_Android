package com.example.price_tag.ui.features.productDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.price_tag.domain.model.product.SingleProductDetails

@Composable
fun ProductDetailsDescriptionContent(
    productDetails: SingleProductDetails,
) {
    Text(
        text = productDetails.description ?: "",
        modifier = Modifier.padding(8.dp),
    )
}