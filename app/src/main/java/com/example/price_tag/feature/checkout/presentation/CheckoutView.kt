package com.example.price_tag.feature.checkout.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.checkout.presentation.components.CheckoutActionBar
import com.example.price_tag.feature.checkout.presentation.components.CheckoutAddressContent
import com.example.price_tag.feature.checkout.presentation.components.CheckoutAddressFormSheet
import com.example.price_tag.feature.checkout.presentation.components.CheckoutContactDetailsSection
import com.example.price_tag.feature.checkout.presentation.components.CheckoutErrorState
import com.example.price_tag.feature.checkout.presentation.components.CheckoutLoadingState
import com.example.price_tag.feature.checkout.presentation.components.CheckoutOrderStatus
import com.example.price_tag.feature.checkout.presentation.components.CheckoutOrderSummaryCard
import com.example.utilites.ResourceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutView(
    cartId: Long,
    onBackClick: () -> Unit,
    checkoutViewModel: CheckoutViewModel = hiltViewModel(),
) {
    val uiState by checkoutViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = cartId) {
        checkoutViewModel.onIntent(CheckoutContract.CheckoutIntent.LoadCheckout(cartId))
    }

    CheckoutScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onIntent = { checkoutViewModel.onIntent(intent = it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CheckoutScreen(
    uiState: CheckoutContract.CheckoutState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onIntent: (CheckoutContract.CheckoutIntent) -> Unit,
) {
    val canEditOrderDetails: Boolean =
        uiState.createOrderState !is ResourceState.Loading && uiState.createOrderState !is ResourceState.Success

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Checkout")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back ",
                        )
                    }
                },
            )
        },
        bottomBar = {
            if (uiState.cartState is ResourceState.Success) {
                val isProcessing =
                    uiState.createOrderState is ResourceState.Loading || uiState.paymentIntentDetailsState is ResourceState.Loading
                val paymentErrorMessage =
                    (uiState.paymentIntentDetailsState as? ResourceState.Error)?.error

                CheckoutActionBar(
                    isLoading = isProcessing,
                    enabled = !isProcessing && uiState.paymentIntentDetailsState !is ResourceState.Success,
                    onContinueClick = {
                        onIntent(CheckoutContract.CheckoutIntent.ContinueToPaymentClicked)
                    },
                    errorMessage = paymentErrorMessage,
                    buttonText = if (paymentErrorMessage != null) "Retry payment setup" else "Continue to payment",
                )
            }
        },
    ) { innerPadding ->
        when (val cartState = uiState.cartState) {
            is ResourceState.Loading -> {
                CheckoutLoadingState()
            }

            is ResourceState.Error -> {
                CheckoutErrorState(
                    errorMessage = cartState.error,
                    onRetry = { onIntent(CheckoutContract.CheckoutIntent.LoadCheckout(uiState.cartId)) },
                )
            }

            is ResourceState.Success -> {
                val cart = cartState.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    if (uiState.createOrderState is ResourceState.Error || uiState.createOrderState is ResourceState.Success) {
                        item(key = "checkout-order-status") {
                            CheckoutOrderStatus(orderState = uiState.createOrderState)
                        }
                    }

                    item(key = "checkout-delivery-address") {
                        CheckoutAddressContent(
                            addressesState = uiState.addressesState,
                            selectedAddressId = uiState.selectedAddressId,
                            onAddressSelected = { address ->
                                onIntent(CheckoutContract.CheckoutIntent.SelectAddress(address))
                            },
                            onAddAddressClick = {
                                onIntent(CheckoutContract.CheckoutIntent.AddAddressClicked)
                            },
                            onRetry = {
                                onIntent(CheckoutContract.CheckoutIntent.GetAddresses)
                            },
                            selectedAddressError = uiState.selectedAddressError,
                            enabled = canEditOrderDetails,
                        )
                    }

                    item(key = "checkout-contact-details") {
                        CheckoutContactDetailsSection(
                            recipientName = uiState.recipientName,
                            phoneNumber = uiState.phoneNumber,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onRecipientNameChange = { value ->
                                onIntent(CheckoutContract.CheckoutIntent.RecipientNameChanged(value))
                            },
                            onPhoneNumberChange = { value ->
                                onIntent(CheckoutContract.CheckoutIntent.PhoneNumberChanged(value))
                            },
                            contactDetailsErrors = uiState.contactDetailsErrors,
                            enabled = canEditOrderDetails,
                        )
                    }

                    item(key = "checkout-order-summary") {
                        CheckoutOrderSummaryCard(
                            itemCount = cart.totalItemCount,
                            subtotal = cart.cartTotal,
                            shippingFee = uiState.shippingFee,
                            currencyCode = uiState.currencyCode,
                            total = cart.cartTotal + (uiState.shippingFee ?: 0.0),
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    if (uiState.isAddressFormVisible) {
        CheckoutAddressFormSheet(
            form = uiState.addressForm,
            isSaving = uiState.createAddressState is ResourceState.Loading,
            onFormChange = { form ->
                onIntent(CheckoutContract.CheckoutIntent.AddressFormChanged(form))
            },
            onDismiss = {
                onIntent(CheckoutContract.CheckoutIntent.DismissAddressForm)
            },
            onSave = {
                onIntent(CheckoutContract.CheckoutIntent.SubmitAddressForm)
            },
            errorMessage = (uiState.createAddressState as? ResourceState.Error)?.error,
            errors = uiState.addressFormErrors,
        )
    }
}


@Preview(
    name = "Checkout",
    showBackground = true,
    widthDp = 360,
    heightDp = 800,
    showSystemUi = true,
)
@Composable
private fun CheckoutScreenPreview() {
    PriceTagTheme {
        CheckoutScreen(
            uiState = CheckoutContract.CheckoutState(
                cartId = 1L,
                cartState = ResourceState.Success(
                    Cart(
                        cartId = 1L,
                        status = "ACTIVE",
                        items = emptyList(),
                        cartTotal = 168.90,
                        totalItemCount = 3L,
                    ),
                ),
            ),
            onBackClick = {},
            onIntent = {},
        )
    }
}

