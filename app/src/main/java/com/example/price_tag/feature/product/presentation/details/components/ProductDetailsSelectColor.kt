package com.example.price_tag.feature.product.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.feature.product.domain.model.ColorSizeVariantGroup
import com.example.price_tag.feature.product.presentation.details.ProductDetailsContract
import com.example.price_tag.feature.product.presentation.details.ProductDetailsViewModel

@Composable
fun ProductDetailsSelectColor(
    productDetailsViewModel: ProductDetailsViewModel,
    productDetailsState: ProductDetailsContract.ProductDetailsState,
    colorSizeVariantGroup: List<ColorSizeVariantGroup>,
) {

    Column {
        Text(
            text = "Select Color",
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items = colorSizeVariantGroup) { data ->
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(data.colorHex.toColorInt()))
                        .border(
                            width = 2.dp,
                            color = if (data.colorHex == productDetailsState.selectedColor) PrimaryColor else NeutralColor100,
                            shape = CircleShape,
                        )
                        .clickable {
                            productDetailsViewModel.onColorSelected(data.colorHex)
                        },
                )
            }
        }

    }

}