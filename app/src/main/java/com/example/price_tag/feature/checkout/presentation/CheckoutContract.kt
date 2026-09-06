package com.example.price_tag.feature.checkout.presentation

import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails
import com.example.utilites.ResourceState

class CheckoutContract {
    sealed interface CheckoutIntent {
        data class LoadCheckout(val cartId: Long) : CheckoutIntent
        data object GetAddresses : CheckoutIntent
        data class CreateAddress(val newAddress: NewAddress) : CheckoutIntent
        data class CreateOrder(val createOrderParams: CreateOrderParams) : CheckoutIntent
        data class InitializePayment(val orderNumber: String) : CheckoutIntent
    }

    data class CheckoutState(
        val cartId: Long = 0L,
        val cartState: ResourceState<Cart> = ResourceState.Initial,
        val addressesState: ResourceState<List<Address>> = ResourceState.Initial,
        val createAddressState: ResourceState<List<Address>> = ResourceState.Initial,
        val createOrderState: ResourceState<CreatedOrder>? = ResourceState.Initial,
        val paymentIntentDetailsState: ResourceState<PaymentIntentDetails>? = ResourceState.Initial,
        val shippingFee: Double? = null,
        val currencyCode: String = "AUD",
    )

    sealed interface CheckoutEffect {
        data class ShowSnackBar(
            val message: String,
        ) : CheckoutEffect

        data class PresentPaymentSheet(
            val clientSecret: String,
        ) : CheckoutEffect
    }
}