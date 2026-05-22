package com.example.price_tag.ui.features.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.price_tag.R
import com.example.price_tag.core.theme.PoppinsTypography
import com.example.price_tag.domain.model.category.SubCategory

@Composable
fun SubCategoryItem(
    subCategory: SubCategory,
    onClick: (Long, String) -> Unit,
) {
    Box(modifier = Modifier
        .padding(horizontal = 8.dp)
        .clickable {
            onClick(
                subCategory.id,
                subCategory.name,
            )
        }) {
        Column { // Load image from URL using Coil
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
            ) {
                AsyncImage(
                    model = subCategory.imageUrl,
                    placeholder = painterResource(R.drawable.carousel1),
                    error = painterResource(id = R.drawable.carousel1),
                    contentDescription = "Subcategory Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(CircleShape),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subCategory.name,
                style = PoppinsTypography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}