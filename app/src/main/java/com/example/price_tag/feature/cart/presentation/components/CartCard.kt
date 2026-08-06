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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.price_tag.R
import com.example.price_tag.core.theme.ErrorColor800
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor800
import com.example.price_tag.feature.cart.domain.model.CartItem

@Composable
fun CartCard(
    cartItem: CartItem,
    onRemove: (cartItemId: Long) -> Unit,
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

                Text(
                    cartItem.variantId.toString(),
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
                            text = " ${cartItem.colorName} / ${cartItem.sizeName}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                        )
                    }

                    Text(
                        text = "SKU: ${cartItem.sku}",
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
                            text = cartItem.quantity.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 14.dp),
                        )

                        IconButton(
                            onClick = {},
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