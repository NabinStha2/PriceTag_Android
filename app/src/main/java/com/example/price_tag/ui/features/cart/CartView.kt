package com.example.price_tag.ui.features.cart

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.price_tag.R
import com.example.price_tag.core.theme.ErrorColor800
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor800


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    navController: NavController?, //    cartViewModel: CartViewModel,
    padding: PaddingValues = PaddingValues(),
) {
    CartView(padding)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartView(parentPadding: PaddingValues = PaddingValues()) {
    Column(modifier = Modifier
        .padding(parentPadding)
        .padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                "Your Cart",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W700,
                ),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "3 items ready for checkout",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 8.dp),
            state = rememberLazyListState(),
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(shape = RoundedCornerShape(corner = CornerSize(10)))
                        .border(
                            width = 1.dp,
                            color = NeutralColor100,
                            shape = RoundedCornerShape(percent = 10),
                        )
                        .padding(8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.carousel1),
                            contentDescription = "Item Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(120.dp)
                                .clip(shape = RoundedCornerShape(corner = CornerSize(10)))
                                .border(
                                    width = 1.dp,
                                    color = NeutralColor100,
                                    shape = RoundedCornerShape(percent = 10),
                                ),
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top,
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Item Name",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.W500,
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                )

                                Row() {
                                    IconButton(
                                        modifier = Modifier
                                            .width(25.dp)
                                            .height(25.dp),
                                        onClick = {},
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Delete the cart item",
                                            tint = ErrorColor800,
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Brand Name",
                                style = MaterialTheme.typography.bodySmall,
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(12.dp)
                                            .height(12.dp)
                                            .background(
                                                color = ErrorColor800,
                                                shape = RoundedCornerShape(100),
                                            ),
                                    )
                                    Text(
                                        text = " Red / 10.5",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                                    )
                                }

                                Text(
                                    "SKU: NK-270-R",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .clip(RoundedCornerShape(percent = 30))
                                    .border(
                                        width = 1.dp,
                                        color = NeutralColor100,
                                        shape = RoundedCornerShape(percent = 30),
                                    ),
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.size(20.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Remove,
                                            contentDescription = "Remove item from cart",
                                            tint = NeutralColor800,
                                        )
                                    }

                                    Text(
                                        text = "1",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(horizontal = 14.dp),
                                    )

                                    IconButton(
                                        onClick = {},
                                        modifier = Modifier.size(20.dp),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Remove item from cart",
                                            tint = NeutralColor800,
                                        )
                                    }
                                }


                            }


                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CartViewPreview() {
    Scaffold() { it ->
        CartView()
    }
}
