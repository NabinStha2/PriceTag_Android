package com.example.price_tag.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.WarningColor

@Preview
@Composable
fun StarRating(
    modifier: Modifier = Modifier,
    rating: Double = 0.0
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(15.dp),
                tint = if (i <= rating) WarningColor else NeutralColor200
            )
        }
    }
}
