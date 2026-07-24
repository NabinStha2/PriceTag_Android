package com.example.price_tag.ui.features.cart

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.price_tag.R
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.Poppins
import com.example.price_tag.core.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    navController: NavController?, //    cartViewModel: CartViewModel,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Cart",
                        fontFamily = Poppins,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CartViewPreview() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = "Cart",
                        fontFamily = Poppins,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 8.dp),
                state = rememberLazyListState(),
            ) {
                items(3) {
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
                            verticalAlignment = Alignment.CenterVertically,
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
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "Item Name das dkas kjd  as kja d cad as jka skj akjsd kajs dkja kj",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.W500,
                                            fontSize = 18.sp,
                                        ),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    Text(
                                        text = "$180.0",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.W500,
                                            color = PrimaryColor,
                                            fontSize = 18.sp,
                                        ),
                                    )
                                }

                                Text(
                                    text = "Size: 10.5",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                )
                                Text(
                                    text = "Color: Red",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                )

                            }
                        }


                    }
                }
            }
        }

    }
}
