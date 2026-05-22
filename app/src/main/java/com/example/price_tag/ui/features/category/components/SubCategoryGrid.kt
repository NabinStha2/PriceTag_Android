package com.example.price_tag.ui.features.category.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.price_tag.R
import com.example.price_tag.core.theme.PoppinsTypography
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.core.theme.WarningColor
import com.example.price_tag.domain.model.category.SubCategory
import com.example.price_tag.ui.components.EmptyState
import com.example.price_tag.ui.components.ErrorState
import com.example.utilites.ResourceState

@Composable
fun SubCategoryGrid(
    subCategoriesState: ResourceState<List<SubCategory>>,
    selectedCategoryName: String?,
    modifier: Modifier = Modifier,
    onSubCategoryClick: (Long, String) -> Unit,
    onRefresh: () -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val pullRefreshState = rememberPullToRefreshState()

    AnimatedContent(
        targetState = subCategoriesState,
        modifier = modifier.fillMaxSize(),
        label = "SubCategoryGridContentAnimation",
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) + slideInHorizontally { it / 2 } togetherWith fadeOut(animationSpec = tween(300))
        },
    ) { subCategoriesState ->
        PullToRefreshBox(
            state = pullRefreshState,
            isRefreshing = subCategoriesState is ResourceState.Loading,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize(),
        ) {
            when (subCategoriesState) {
                is ResourceState.Loading -> SubCategoryLoadingState()

                is ResourceState.Error -> ErrorState(
                    errorMessage = (subCategoriesState.error as? String) ?: "Unknown error",
                    onRetry = onRetry,
                )

                is ResourceState.Success -> {
                    if (subCategoriesState.data.isEmpty()) {
                        EmptyState()
                    } else {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            state = rememberLazyGridState(),
                            columns = GridCells.Adaptive(80.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Column(modifier = Modifier.padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp,
                                )) {
                                    Text(
                                        text = selectedCategoryName ?: "Categories",

                                        style = PoppinsTypography.titleLarge,
                                    )
                                    Text(
                                        text = "Description of the categories",

                                        style = PoppinsTypography.bodyMedium,
                                    )
                                }
                            }

                            items(subCategoriesState.data.size) { index ->
                                SubCategoryItem(
                                    subCategory = subCategoriesState.data[index],
                                    onClick = onSubCategoryClick,
                                )
                            }

                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp,
                                                 vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Trending $selectedCategoryName",
                                        style = PoppinsTypography.titleMedium.copy(fontWeight = FontWeight.W600),
                                        modifier = Modifier.weight(1f),
                                    )
                                    TextButton(
                                        onClick = {},
                                    ) {
                                        Text(
                                            text = "See All",
                                            style = PoppinsTypography.labelLarge,
                                        )
                                    }
                                }
                            }

                            items(
                                span = { GridItemSpan(maxLineSpan) },
                                count = 1,
                            ) {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp),
                                ) {
                                    items(5) {
                                        TrendingProductItem(
                                            selectedCategoryName = selectedCategoryName,
                                            onClick = {},
                                        )
                                    }
                                }

                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun TrendingProductItem(
    selectedCategoryName: String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp,
                     vertical = 4.dp)
            .border(
                width = 1.dp,
                color = PrimaryColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(size = 20.dp),
            )
            .clip(RoundedCornerShape(size = 20.dp))
            .background(
                PrimaryColor.copy(alpha = 0.05f),
            ),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(size = 20.dp)),
                model = "https://images.unsplash.com/photo-1506744038136-462738eaee9c?ixlib=rb-4.0.3&ixid=M3w5dHwxfHx8fHx8fHwxNjg4ODQyODA4&auto=format&fit=crop&w=800&q=60",
                contentDescription = "Trending $selectedCategoryName Product Images",
                placeholder = painterResource(R.drawable.carousel1),
                error = painterResource(R.drawable.carousel1), //                                    clipToBounds = true,
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxSize(),
                text = "Trending $selectedCategoryName Product Name",
                style = PoppinsTypography.bodyMedium.copy(fontWeight = FontWeight.W600),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = WarningColor,
                )
                Text(
                    text = "4.9 (120 reviews)",
                    style = PoppinsTypography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "$199.99",
                        style = PoppinsTypography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    )
                    Text(
                        text = "$249.99",
                        textDecoration = TextDecoration.LineThrough,
                        style = PoppinsTypography.bodyMedium,
                    )
                }

                IconButton(
                    onClick = { onClick() },
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = "Add to Cart",
                        tint = PrimaryColor,
                    )
                }
            }

        }
    }
}

@Composable
private fun SubCategoryLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

