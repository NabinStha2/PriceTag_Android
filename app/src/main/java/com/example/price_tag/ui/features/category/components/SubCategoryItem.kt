package com.example.price_tag.ui.features.category.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.R
import com.example.price_tag.domain.model.category.SubCategory

@Composable
fun SubCategoryItem(
    subCategory: SubCategory,
    onClick: (Long) -> Unit,
) {
    Box(modifier = Modifier
        .padding(horizontal = 8.dp)
        .clickable { onClick(subCategory.id) }) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.carousel1),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subCategory.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}