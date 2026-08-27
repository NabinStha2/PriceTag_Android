package com.example.price_tag.feature.cart.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.model.CartItem
import com.example.price_tag.feature.cart.presentation.components.CartContent
import com.example.price_tag.feature.cart.presentation.components.CartEmptyState
import com.example.price_tag.feature.cart.presentation.components.CartErrorState
import com.example.price_tag.feature.cart.presentation.components.CartLoadingState
import com.example.utilites.ResourceState

@Composable
fun CartView(
    navController: NavController?,
    cartViewModel: CartViewModel,
) {
    val uiState by cartViewModel.cartState.collectAsStateWithLifecycle()

    CartScreen(
        uiState = uiState,
        onIntent = cartViewModel::onIntent,
    )
}

@Composable
internal fun CartScreen(
    uiState: CartContract.CartState,
    onIntent: (CartContract.CartIntent) -> Unit,
) {
    when (val cartState = uiState.getAllCartItems) {
        is ResourceState.Loading -> CartLoadingState()

        is ResourceState.Error -> CartErrorState(
            errorMessage = cartState.error,
            onRetry = {
                onIntent(CartContract.CartIntent.GetAllCartItems)
            },
        )

        is ResourceState.Success -> {
            val cart = cartState.data

            if (cart.items.isEmpty()) {
                CartEmptyState(
                    onRefresh = {
                        onIntent(
                            CartContract.CartIntent.RefreshCart(
                                pullToRefresh = false,
                            ),
                        )
                    },
                )
            } else {
                CartContent(
                    cart = cart,
                    isRefreshing = uiState.isRefreshing,
                    isUpdating = uiState.isUpdating,
                    removingItemIds = uiState.removingItemIds,
                    onIntent = onIntent,
                )
            }
        }

        else -> Unit
    }
}


@Preview(
    name = "Cart - Populated",
    showBackground = true,
    widthDp = 360,
    heightDp = 800,
)
@Composable
private fun CartViewPreview() {
    val previewCart = Cart(
        cartId = 1L,
        status = "ACTIVE",
        items = listOf(
            CartItem(
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
            ),
            CartItem(
                itemId = 102L,
                variantId = 1002L,
                sku = "SHOES-WHT-42",
                productName = "Everyday Comfort Sneakers",
                imageUrl = null,
                colorName = "White",
                colorHex = "#FFFFFF",
                sizeName = "42",
                unitPrice = 89.00,
                quantity = 1L,
                lineTotal = 89.00,
                stockAvailable = 4L,
            ),
        ),
        cartTotal = 168.90,
        totalItemCount = 3L,
    )

    PriceTagTheme {
        CartScreen(
            uiState = CartContract.CartState(
                getAllCartItems = ResourceState.Success(previewCart),
            ),
            onIntent = {},
        )
    }
}



