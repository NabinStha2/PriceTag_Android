package com.example.price_tag.feature.checkout.presentation

import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails
import com.example.price_tag.feature.checkout.presentation.model.AddressFormErrors
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState
import com.example.price_tag.feature.checkout.presentation.model.ContactDetailsErrors
import com.example.utilites.ResourceState

class CheckoutContract {
    sealed interface CheckoutIntent {
        data class LoadCheckout(val cartId: Long) : CheckoutIntent
        data object GetAddresses : CheckoutIntent
        data class SelectAddress(val address: Address) : CheckoutIntent
        data object AddAddressClicked : CheckoutIntent
        data class AddressFormChanged(val addressForm: AddressFormState) : CheckoutIntent
        data object DismissAddressForm : CheckoutIntent

        //creating a new address
        data object SubmitAddressForm : CheckoutIntent
        data class RecipientNameChanged(val recipientName: String) : CheckoutIntent
        data class PhoneNumberChanged(val phoneNumber: String) : CheckoutIntent
        data object ContinueToPaymentClicked : CheckoutIntent
        data object PaymentSheetCompleted : CheckoutIntent
        data object PaymentSheetCanceled : CheckoutIntent
        data class PaymentSheetFailed(val message: String) : CheckoutIntent
    }

    data class CheckoutState(
        val cartId: Long = 0L,
        val cartState: ResourceState<Cart> = ResourceState.Initial,
        val addressesState: ResourceState<List<Address>> = ResourceState.Initial,
        val createAddressState: ResourceState<List<Address>> = ResourceState.Initial,
        val shippingFee: Double? = null,
        val currencyCode: String = "AUD",
        val isAddressFormVisible: Boolean = false,
        val addressForm: AddressFormState = AddressFormState(),
        val addressFormErrors: AddressFormErrors = AddressFormErrors(),
        val recipientName: String = "",
        val phoneNumber: String = "",
        val contactDetailsErrors: ContactDetailsErrors = ContactDetailsErrors(),
        val selectedAddressId: Long? = null,
        val selectedAddressError: String? = null,
        val hasSubmittedAddressForm: Boolean = false,
        val createOrderState: ResourceState<CreatedOrder> = ResourceState.Initial,
        val paymentIntentDetailsState: ResourceState<PaymentIntentDetails> = ResourceState.Initial,
        val isAwaitingPaymentConfirmation: Boolean = false,
        val paymentResultMessage: String? = null,
        val isPaymentSheetOpen: Boolean = false,
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