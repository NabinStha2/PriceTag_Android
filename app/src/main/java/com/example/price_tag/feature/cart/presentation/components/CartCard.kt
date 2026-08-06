package com.example.price_tag.feature.cart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.price_tag.R
import com.example.price_tag.core.theme.ErrorColor800
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor200
import com.example.price_tag.core.theme.NeutralColor400
import com.example.price_tag.core.theme.NeutralColor50
import com.example.price_tag.core.theme.NeutralColor800
import com.example.price_tag.core.theme.NeutralColor900
import com.example.price_tag.feature.cart.domain.model.CartItem

@Composable
fun CartCard(
    cartItem: CartItem,
    onRemove: (cartItemId: Long) -> Unit,
    onQuantityChange: (cartItemId: Long, quantity: Int) -> Unit,
) {
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
                        text = cartItem.productName,
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
                            onClick = { onRemove(cartItem.itemId) },
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

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = NeutralColor100,
                                shape = RoundedCornerShape(percent = 20),
                            )
                            .background(
                                color = NeutralColor50,
                                shape = RoundedCornerShape(percent = 20),
                            )
                            .padding(
                                horizontal = 6.dp,
                                vertical = 4.dp,
                            ),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .height(12.dp)
                                    .background(
                                        color = Color(cartItem.colorHex!!.toColorInt()),
                                        shape = RoundedCornerShape(100),
                                    ),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${cartItem.colorName}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                            )
                        }

                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = NeutralColor100,
                                shape = RoundedCornerShape(percent = 20),
                            )
                            .background(
                                color = NeutralColor50,
                                shape = RoundedCornerShape(percent = 20),
                            )
                            .padding(
                                horizontal = 6.dp,
                                vertical = 4.dp,
                            ),
                    ) {
                        Text(
                            text = "${cartItem.sizeName}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "SKU: ${cartItem.sku}",
                    style = MaterialTheme.typography.bodySmall,
                    color = NeutralColor400,
                )

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
                            onClick = {
                                onQuantityChange(
                                    cartItem.itemId,
                                    cartItem.quantity.toInt() - 1,
                                )
                            },
                            modifier = Modifier.size(20.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = "Remove item from cart",
                                tint = NeutralColor800,
                            )
                        }

                        Text(
                            text = cartItem.quantity.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 14.dp),
                        )

                        IconButton(
                            onClick = {
                                onQuantityChange(
                                    cartItem.itemId,
                                    cartItem.quantity.toInt() + 1,
                                )
                            },
                            modifier = Modifier.size(20.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add item to cart",
                                tint = NeutralColor800,
                            )
                        }
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartCardPreview() {
    CartCard(
        cartItem = CartItem(itemId = 1L,
                            variantId = 101L,
                            sku = "SKU12345",
                            productName = "Sample Product Name that is quite long to test ellipsis and layout behavior",
                            imageUrl = "",
                            colorName = "Red",
                            colorHex = "#FF0000",
                            sizeName = "M",
                            unitPrice = 29.99,
                            quantity = 2,
                            lineTotal = 59.98,
                            stockAvailable = 10),
        onRemove = {},
        onQuantityChange = { _, _ -> },
    )
}
