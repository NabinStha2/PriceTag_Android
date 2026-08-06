package com.example.price_tag.feature.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.core.ui.components.ErrorState
import com.example.price_tag.core.ui.viewmodel.SelectedCategoryViewModel
import com.example.price_tag.feature.cart.domain.model.CartItem
import com.example.price_tag.feature.cart.presentation.components.CartCard
import com.example.utilites.ResourceState


@Composable
fun CartView(
    navController: NavController?,
    cartViewModel: CartViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val uiState by cartViewModel.cartState.collectAsStateWithLifecycle()

    CartView(
        uiState = uiState,
        onIntent = cartViewModel::onIntent,
    )
}

@Composable
private fun CartView(
    uiState: CartContract.CartState,
    onIntent: (CartContract.CartIntent) -> Unit,
) {

    when (val getAllCartItemsState = uiState.getAllCartItems) {
        is ResourceState.Loading -> CartLoadingState()

        is ResourceState.Error -> CartErrorState(
            errorMessage = getAllCartItemsState.error,
            onRetryAction = {
                onIntent(CartContract.CartIntent.GetAllCartItems)
            },
        )

        is ResourceState.Success -> CartSuccessState(
            cartItems = getAllCartItemsState.data.items,
            onIntent = onIntent,
            isRefreshing = uiState.isRefreshing,
        )

        else -> {}
    }
}

@Composable
private fun CartLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CartErrorState(
    errorMessage: String,
    onRetryAction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        ErrorState(
            errorMessage = errorMessage,
            onRetry = onRetryAction,
        )
    }
}


@Composable
fun CartSuccessState(
    cartItems: List<CartItem>,
    onIntent: (CartContract.CartIntent) -> Unit,
    isRefreshing: Boolean,
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
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
                "${cartItems.size} items ready for checkout",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        PullToRefreshBox(
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    state = pullToRefreshState,
                    containerColor = MaterialTheme.colorScheme.surface,
                    color = PrimaryColor,
                )
            },
            onRefresh = {
                onIntent(CartContract.CartIntent.RefreshCart)
            },
            isRefreshing = isRefreshing,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = 8.dp),
                state = rememberLazyListState(),
            ) {
                items(cartItems.size) { index ->
                    CartCard(
                        cartItem = cartItems[index],
                        onRemove = { cartItemId ->
                            onIntent(CartContract.CartIntent.RemoveCartItem(cartItemId))
                        },
                    )
                }
            }
        }

        ElevatedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = PrimaryColor,
                contentColor = NeutralColor100,
            ),
        ) {
            Text(text = "Checkout")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CartViewPreview() { //    Scaffold() { it ->
    //        CartView()
    //    }
}
