package com.example.price_tag.ui.features.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.price_tag.R
import com.example.price_tag.core.theme.AccentColor
import com.example.price_tag.core.theme.PrimaryColor
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
            .width(100.dp)
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
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Composable
private fun CategoryNavigationRailItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationRailItem(
        modifier = Modifier
            .padding(vertical = 4.dp,
                     horizontal = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp)),
        selected = isSelected,
        onClick = onClick,
        icon = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
//                if (isSelected) {
//                    Box(
//                        modifier = Modifier
//                            .width(6.dp)
//                            .fillMaxHeight(0.8f)
//                            .clip(RoundedCornerShape(6.dp))
//                            .background(color = AccentColor),
//                    )
//                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = if (isSelected) 4.dp else 0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(58.dp)
                            .padding(bottom = 8.dp)
                            .clip(CircleShape),
                    ) {
                        AsyncImage(
                            model = category.imageUrl,
                            contentDescription = "Category Image",
                            placeholder = painterResource(id = R.drawable.carousel1),
                            error = painterResource(id = R.drawable.carousel1),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Text(
                        text = category.name,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        alwaysShowLabel = false,
        label = null,
        colors = NavigationRailItemDefaults.colors(
            indicatorColor = PrimaryColor,
            selectedIconColor = Color.White,
            unselectedIconColor = Color.Black,
        ),
    )
}

@Preview(showBackground = true,
         backgroundColor = 0xFFFFFFFF,
         widthDp = 120,
         heightDp = 600)
@Composable
fun CategoryNavigationRailPreview() {
    val sampleCategories = listOf(
        Category(id = 1,
                 name = "Electronics",
                 imageUrl = null),
        Category(id = 2,
                 name = "Clothing",
                 imageUrl = null),
        Category(id = 3,
                 name = "Food",
                 imageUrl = null),
        Category(id = 4,
                 name = "Books",
                 imageUrl = null),
        Category(id = 5,
                 name = "Sports",
                 imageUrl = null),
    )

    CategoryNavigationRail(categories = sampleCategories,
                           selectedIndex = 0,
                           onCategorySelected = { _, _ -> })
}
