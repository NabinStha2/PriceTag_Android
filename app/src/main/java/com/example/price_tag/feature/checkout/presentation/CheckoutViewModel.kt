package com.example.price_tag.feature.checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.feature.cart.domain.model.Cart
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.usecase.CreateAddressUseCase
import com.example.price_tag.feature.checkout.domain.usecase.CreateOrderUseCase
import com.example.price_tag.feature.checkout.domain.usecase.GetAddressesUseCase
import com.example.price_tag.feature.checkout.domain.usecase.InitializePaymentUseCase
import com.example.price_tag.feature.checkout.presentation.mapper.toNewAddress
import com.example.price_tag.feature.checkout.presentation.model.AddressFormErrors
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState
import com.example.price_tag.feature.checkout.presentation.model.ContactDetailsErrors
import com.example.price_tag.feature.checkout.presentation.validation.validate
import com.example.price_tag.feature.checkout.presentation.validation.validateCheckoutContactDetails
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val getAllAddressesUseCase: GetAddressesUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val paymentInitializePaymentUseCase: InitializePaymentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CheckoutContract.CheckoutState())
    val state: StateFlow<CheckoutContract.CheckoutState> = _state.asStateFlow()

    private val _effects = Channel<CheckoutContract.CheckoutEffect>(Channel.BUFFERED)
    val effect = _effects.receiveAsFlow()

    private var cartJob: Job? = null
    private var addressJob: Job? = null
    private var createAddressJob: Job? = null
    private var createOrderJob: Job? = null
    private var initializePaymentJob: Job? = null

    fun onIntent(intent: CheckoutContract.CheckoutIntent) {
        val changesOrderDetails = when (intent) {
            is CheckoutContract.CheckoutIntent.RecipientNameChanged, is CheckoutContract.CheckoutIntent.PhoneNumberChanged, is CheckoutContract.CheckoutIntent.SelectAddress, CheckoutContract.CheckoutIntent.AddAddressClicked, is CheckoutContract.CheckoutIntent.AddressFormChanged, CheckoutContract.CheckoutIntent.SubmitAddressForm -> true

            else -> false
        }

        if (areOrderDetailsLocked() && changesOrderDetails) {
            return
        }


        when (intent) {
            is CheckoutContract.CheckoutIntent.LoadCheckout -> {
                loadCheckout(intent.cartId)

                if (intent.cartId > 0L) {
                    loadAddresses()
                }
            }

            is CheckoutContract.CheckoutIntent.GetAddresses -> {
                loadAddresses()
            }

            is CheckoutContract.CheckoutIntent.SelectAddress -> {
                selectAddress(addressId = intent.address.id)
            }

            is CheckoutContract.CheckoutIntent.AddAddressClicked -> {
                addAddressClicked()
            }

            is CheckoutContract.CheckoutIntent.AddressFormChanged -> {
                if (createAddressJob?.isActive == true) {
                    return
                }
                _state.update { currentState ->
                    currentState.copy(
                        addressForm = intent.addressForm,
                        createAddressState = ResourceState.Initial,
                        addressFormErrors = if (currentState.hasSubmittedAddressForm) {
                            intent.addressForm.validate()
                        } else {
                            AddressFormErrors()
                        },
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.DismissAddressForm -> {
                _state.update { currentState ->
                    currentState.copy(
                        isAddressFormVisible = false,
                        addressForm = AddressFormState(),
                        addressFormErrors = AddressFormErrors(),
                        createAddressState = ResourceState.Initial,
                        hasSubmittedAddressForm = false,
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.SubmitAddressForm -> {
                submitAddressForm()
            }

            is CheckoutContract.CheckoutIntent.RecipientNameChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        recipientName = intent.recipientName,
                        contactDetailsErrors = currentState.contactDetailsErrors.copy(recipientName = null),
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.PhoneNumberChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        phoneNumber = intent.phoneNumber,
                        contactDetailsErrors = currentState.contactDetailsErrors.copy(phoneNumber = null),
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.ContinueToPaymentClicked -> {
                placeOrder()
            }

            CheckoutContract.CheckoutIntent.PaymentSheetCompleted -> {
                _state.update {
                    it.copy(
                        isAwaitingPaymentConfirmation = true,
                        paymentResultMessage = "Payment submitted. Checking order status…",
                    )
                }
            }

            CheckoutContract.CheckoutIntent.PaymentSheetCanceled -> {
                _state.update {
                    it.copy(
                        isAwaitingPaymentConfirmation = false,
                        paymentResultMessage = "Payment cancelled. You can try again.",
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.PaymentSheetFailed -> {
                _state.update {
                    it.copy(
                        isAwaitingPaymentConfirmation = false,
                        paymentResultMessage = intent.message,
                    )
                }
            }
        }
    }

    private fun areOrderDetailsLocked(): Boolean {
        val orderState = _state.value.createOrderState

        return orderState is ResourceState.Loading || orderState is ResourceState.Success
    }

    private fun initializePayment(orderNumber: String) {
        if (initializePaymentJob?.isActive == true) {
            return
        }

        if (_state.value.paymentIntentDetailsState is ResourceState.Success) {
            return
        }

        initializePaymentJob = viewModelScope.launch {
            paymentInitializePaymentUseCase(orderNumber).collectLatest { response ->
                _state.update { currentState ->
                    currentState.copy(paymentIntentDetailsState = response)
                }

                when (response) {
                    is ResourceState.Success -> {
                        _effects.send(CheckoutContract.CheckoutEffect.PresentPaymentSheet(response.data.clientSecret))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun placeOrder() {
        if (createOrderJob?.isActive == true) return

        val currentState: CheckoutContract.CheckoutState = _state.value

        val existingOrder: ResourceState<CreatedOrder> = currentState.createOrderState
        if (existingOrder is ResourceState.Success) {
            initializePayment(existingOrder.data.orderNumber)
            return
        }

        if (createAddressJob?.isActive == true) return

        val cartState: ResourceState<Cart> = currentState.cartState
        if (cartState !is ResourceState.Success) return
        if (cartState.data.items.isEmpty()) {
            _state.update {
                it.copy(createOrderState = ResourceState.Error("Your cart is empty"))
            }
            return
        }

        if (!validateCheckout()) return

        val validatedState = _state.value
        val addressId = validatedState.selectedAddressId ?: return

        val createOrderParams: CreateOrderParams = CreateOrderParams(
            addressId = addressId,
            recipientName = currentState.recipientName.trim(),
            phoneNumber = currentState.phoneNumber.trim(),
        )

        _state.update {
            it.copy(createOrderState = ResourceState.Loading)
        }

        createOrderJob = viewModelScope.launch {
            createOrderUseCase(createOrderParams).collectLatest { response ->
                _state.update { currentState ->
                    currentState.copy(createOrderState = response)
                }

                if (response is ResourceState.Success) {
                    initializePayment(response.data.orderNumber)

                }

            }
        }
    }

    private fun loadCheckout(cartId: Long) {
        if (cartId <= 0L) {
            _state.update {
                it.copy(
                    cartState = ResourceState.Error("Invalid cart"),
                )
            }
            return
        }

        cartJob?.cancel()

        cartJob = viewModelScope.launch {
            _state.update { it.copy(cartId = cartId) }

            getAllCartItemsUseCase().collectLatest { response ->
                val cartState = when (response) {
                    is ResourceState.Success -> {
                        if (response.data.cartId == cartId) {
                            response
                        } else {
                            ResourceState.Error("Cart is no longer available")
                        }
                    }

                    else -> response
                }
                _state.update { it.copy(cartState = cartState) }
            }
        }
    }

    private fun selectAddress(addressId: Long) {
        _state.update { currentState ->
            val addressesState: ResourceState<List<Address>> = currentState.addressesState
            if (addressesState is ResourceState.Success && addressesState.data.any { it.id == addressId }) {
                currentState.copy(
                    selectedAddressId = addressId,
                    selectedAddressError = null,
                )
            } else {
                currentState
            }
        }
    }

    private fun addAddressClicked() {
        _state.update { currentState -> currentState.copy(isAddressFormVisible = true) }
    }

    private fun loadAddresses() {
        if (addressJob?.isActive == true) {
            return
        }

        addressJob = viewModelScope.launch {
            getAllAddressesUseCase().collectLatest { response ->
                _state.update { currentState ->
                    when (response) {
                        is ResourceState.Success -> {
                            val addresses: List<Address> = response.data

                            val selectedAddress: Address? = addresses.firstOrNull {
                                it.id == currentState.selectedAddressId
                            } ?: addresses.firstOrNull {
                                it.isDefault
                            }
                            currentState.copy(
                                addressesState = response,
                                selectedAddressId = selectedAddress?.id,
                            )
                        }

                        else -> {
                            currentState.copy(addressesState = response)
                        }
                    }
                }
            }
        }
    }

    private fun submitAddressForm() {
        if (createAddressJob?.isActive == true) {
            return
        }

        val addressForm: AddressFormState = _state.value.addressForm
        val errors: AddressFormErrors = addressForm.validate()

        _state.update {
            it.copy(
                addressFormErrors = errors,
                hasSubmittedAddressForm = true,
            )
        }

        if (!errors.isValid) {
            _state.update { currentState ->
                currentState.copy(
                    createAddressState = ResourceState.Initial,
                )
            }
            return
        }

        val newAddress: NewAddress = addressForm.toNewAddress()

        _state.update {
            it.copy(
                addressFormErrors = AddressFormErrors(),
                createAddressState = ResourceState.Loading,
            )
        }

        createAddressJob = viewModelScope.launch {
            createAddressUseCase(newAddress).collectLatest { response ->
                _state.update { currentState ->
                    when (response) {
                        is ResourceState.Success -> {
                            val createdAddress: Address? = response.data.firstOrNull { address ->
                                address.street == newAddress.street && address.city == newAddress.city && address.state == newAddress.state && address.country == newAddress.country && address.postalCode == newAddress.postalCode
                            }

                            currentState.copy(
                                createAddressState = response,
                                addressesState = response,
                                selectedAddressId = createdAddress?.id
                                                    ?: currentState.selectedAddressId,
                                isAddressFormVisible = false,
                                addressForm = AddressFormState(),
                                addressFormErrors = AddressFormErrors(),
                                hasSubmittedAddressForm = false,
                            )
                        }

                        else -> currentState.copy(
                            createAddressState = response,
                        )
                    }
                }
            }
        }
    }

    private fun validateCheckout(): Boolean {
        val currentState = _state.value

        val contactErrors: ContactDetailsErrors = validateCheckoutContactDetails(
            recipientName = currentState.recipientName,
            phoneNumber = currentState.phoneNumber,
        )

        val addressesState: ResourceState<List<Address>> = currentState.addressesState
        val hasSelectedAddress: Boolean =
            addressesState is ResourceState.Success && addressesState.data.any {
                it.id == currentState.selectedAddressId
            }

        val addressError: String? = if (hasSelectedAddress) {
            null
        } else {
            "Select a delivery address"
        }

        _state.update {
            it.copy(
                contactDetailsErrors = contactErrors,
                selectedAddressError = addressError,
            )
        }

        return contactErrors.isValid && hasSelectedAddress
    }
}