package com.example.price_tag.feature.cart.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.price_tag.feature.cart.data.model.UpdateCartItemQuantityRequestDto
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.presentation.CartContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    cart: Cart,
    isRefreshing: Boolean,
    isUpdating: Boolean,
    removingItemIds: Set<Long>,
    onIntent: (CartContract.CartIntent) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val itemLabel = if (cart.totalItemCount == 1L) "item" else "items"

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CartHeader(
            totalItemCount = cart.totalItemCount,
            itemLabel = itemLabel,
        )

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                onIntent(
                    CartContract.CartIntent.RefreshCart(
                        pullToRefresh = true,
                    ),
                )
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                ),
                state = rememberLazyListState(),
            ) {
                items(
                    items = cart.items,
                    key = { cartItem -> cartItem.itemId },
                ) { cartItem ->
                    CartCard(
                        cartItem = cartItem,
                        isRemoving = cartItem.itemId in removingItemIds,
                        isQuantityUpdating = isUpdating,
                        onRemove = { cartItemId ->
                            onIntent(
                                CartContract.CartIntent.RemoveCartItem(
                                    cartItemId = cartItemId,
                                ),
                            )
                        },
                        onQuantityChange = { cartItemId, quantity ->
                            onIntent(
                                CartContract.CartIntent.UpdateCartItemQuantity(
                                    UpdateCartItemQuantityRequestDto(
                                        cartItemId = cartItemId,
                                        quantity = quantity,
                                    ),
                                ),
                            )
                        },
                    )
                }
            }
        }

        CartCheckoutBar(
            cartTotal = cart.cartTotal,
            enabled = !isUpdating && removingItemIds.isEmpty(),
            onCheckoutClick = {
                // Checkout navigation is implemented in the next cart task.
            },
        )
    }
}

@Composable
private fun CartHeader(
    totalItemCount: Long,
    itemLabel: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$totalItemCount $itemLabel ready for checkout",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
