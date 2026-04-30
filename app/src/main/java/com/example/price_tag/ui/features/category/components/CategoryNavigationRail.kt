package com.example.price_tag.ui.features.category.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.domain.model.category.Category


@Composable
fun CategoryNavigationRail(
    categories: List<Category>,
    selectedIndex: Int,
    onCategorySelected: (Int, Category) -> Unit,
) {
    NavigationRail(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .width(120.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFF8F8F8),
        contentColor = Color.Black,
    ) {
        categories.forEachIndexed { index, category ->
            CategoryNavigationRailItem(
                category = category,
                isSelected = selectedIndex == index,
                onClick = {
                    onCategorySelected(index,
                                       category)
                },
            )
        }
    }
}

@Composable
private fun CategoryNavigationRailItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp,
                     horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        NavigationRailItem(
            selected = isSelected,
            onClick = onClick,
            icon = {
                Text(
                    text = category.name,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )
            },
            colors = NavigationRailItemDefaults.colors(
                indicatorColor = Color(0xFF3F51B5),
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black,
            ),
        )
    }
}