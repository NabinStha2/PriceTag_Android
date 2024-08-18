package com.example.price_tag.ui.features.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.R
import com.example.price_tag.ui.features.home.ProductItem
import com.example.price_tag.ui.features.home.widgets.ProductCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductView(
    navController: NavController = rememberNavController(),
) {
    val productList = listOf(
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
        ProductItem(
            name = "Product 1",
            price = "$10",
            categoryName = "Category 1",
            imageResId = R.drawable.carousel1,
            isFav = false
        ),
        ProductItem(
            name = "Product 2",
            price = "$20",
            categoryName = "Category 2",
            imageResId = R.drawable.carousel2,
            isFav = false
        ),
        ProductItem(
            name = "Product 3",
            price = "$30",
            categoryName = "Category 3",
            imageResId = R.drawable.carousel3,
            isFav = false
        ),
    )

    var productSearchValue by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            Row {
                OutlinedTextField(
                    value = productSearchValue,
                    onValueChange = { value ->
                        productSearchValue = value
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(6f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {}
                    ),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Search") },
                    trailingIcon = {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .height(56.dp)
                                .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Black,
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            Color.White,
                            RoundedCornerShape(12.dp),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Search Icon",
                        tint = Color.Black,
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                itemsIndexed(items = productList) { index, product ->
                    ProductCard(index = index, productItem = product)
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun ProductViewPreview() {
    ProductView()
}