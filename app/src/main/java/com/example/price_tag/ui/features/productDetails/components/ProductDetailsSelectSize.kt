package com.example.price_tag.ui.features.productDetails.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor800
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.domain.model.product.ColorSizeVariantGroup
import com.example.price_tag.ui.features.productDetails.ProductDetailsContract
import com.example.price_tag.ui.features.productDetails.ProductDetailsViewModel

@Composable
fun ProductDetailsSelectSize(
    productDetailsViewModel: ProductDetailsViewModel,
    productDetailsState: ProductDetailsContract.ProductDetailsState,
    colorSizeVariantGroup: List<ColorSizeVariantGroup>,

    ) {
    Column {
        Text(
            text = "Select Size",
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        val selectedGroup: ColorSizeVariantGroup? =
            colorSizeVariantGroup.firstOrNull { it.colorHex == productDetailsState.selectedColor }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(selectedGroup?.variants ?: emptyList()) { productVariant ->
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(shape = RoundedCornerShape(percent = 15))
                        .clickable {
                            productDetailsViewModel.onSizeSelected(sizeId = productVariant.size?.id?.toInt()
                                                                            ?: -1,
                                                                   productVariants = productVariant,
                                                                   colorSizeVariantGroup = colorSizeVariantGroup)
                        }
                        .background(color = if (productDetailsState.selectedSize == productVariant.size?.id?.toInt()) PrimaryColor else Color.Transparent)
                        .border(
                            shape = RoundedCornerShape(percent = 15),
                            width = 2.dp,
                            color = NeutralColor100,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = productVariant.size?.name ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (productDetailsState.selectedSize == productVariant.size?.id?.toInt()) Color.White else NeutralColor800,
                    )
                }
            }
        }

    }
}