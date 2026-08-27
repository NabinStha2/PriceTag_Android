package com.example.price_tag.feature.cart.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.example.price_tag.R
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.feature.cart.domain.model.CartItem

@Composable
fun CartCard(
    cartItem: CartItem,
    onRemove: (cartItemId: Long) -> Unit,
    onQuantityChange: (cartItemId: Long, quantity: Int) -> Unit,
    isRemoving: Boolean = false,
    isQuantityUpdating: Boolean = false,
) {
    val color = remember(cartItem.colorHex) {
        cartItem.colorHex?.let { colorHex ->
            runCatching {
                Color(colorHex.toColorInt())
            }.getOrNull()
        }
    }

    val controlsEnabled = !isRemoving && !isQuantityUpdating

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            AsyncImage(
                model = cartItem.imageUrl,
                contentDescription = cartItem.productName,
                placeholder = painterResource(R.drawable.carousel1),
                error = painterResource(R.drawable.carousel1),
                fallback = painterResource(R.drawable.carousel1),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = cartItem.productName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    if (isRemoving) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(20.dp),
                            strokeWidth = 2.dp,
                        )
                    } else {
                        IconButton(
                            enabled = controlsEnabled,
                            onClick = {
                                onRemove(cartItem.itemId)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove ${cartItem.productName}",
                                tint = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                if (!cartItem.colorName.isNullOrBlank() || !cartItem.sizeName.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        cartItem.colorName?.takeIf { it.isNotBlank() }
                            ?.let { colorName ->
                                VariantChip {
                                    if (color != null) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.colorScheme.outline,
                                                    shape = CircleShape,
                                                ),
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))
                                    }

                                    Text(
                                        text = colorName,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            }

                        if (!cartItem.colorName.isNullOrBlank() && !cartItem.sizeName.isNullOrBlank()) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        cartItem.sizeName?.takeIf { it.isNotBlank() }
                            ?.let { sizeName ->
                                VariantChip {
                                    Text(
                                        text = "Size $sizeName",
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            }
                    }
                }

                Text(
                    text = "SKU: ${cartItem.sku}",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = formatCartPrice(cartItem.lineTotal),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        if (cartItem.quantity > 1) {
                            Text(
                                text = "${formatCartPrice(cartItem.unitPrice)} each",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    QuantitySelector(
                        quantity = cartItem.quantity,
                        enabled = controlsEnabled,
                        canDecrease = cartItem.quantity > 1,
                        canIncrease = cartItem.quantity < cartItem.stockAvailable,
                        onDecrease = {
                            onQuantityChange(
                                cartItem.itemId,
                                cartItem.quantity.toInt() - 1,
                            )
                        },
                        onIncrease = {
                            onQuantityChange(
                                cartItem.itemId,
                                cartItem.quantity.toInt() + 1,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun VariantChip(
    content: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Long,
    enabled: Boolean,
    canDecrease: Boolean,
    canIncrease: Boolean,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                enabled = enabled && canDecrease,
                onClick = onDecrease,
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease quantity",
                )
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )

            IconButton(
                enabled = enabled && canIncrease,
                onClick = onIncrease,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase quantity",
                )
            }
        }
    }
}


@Preview(
    name = "Cart Card - Populated",
    showBackground = true,
    widthDp = 360,
    heightDp = 200,
)
@Composable
fun CartCardPreview() {
    val previewCartItem: CartItem = CartItem(
        itemId = 101L,
        variantId = 1001L,
        sku = "TSHIRT-BLK-M",
        productName = "Classic Cotton T-Shirt",
        imageUrl = null,
        colorName = "Black",
        colorHex = "#000000",
        sizeName = "M",
        unitPrice = 39.95,
        quantity = 2L,
        lineTotal = 79.90,
        stockAvailable = 8L,
    );
    PriceTagTheme() {
        CartCard(
            cartItem = previewCartItem,
            onRemove = {},
            onQuantityChange = { _, _ -> },
        )
    }
}
