package com.example.price_tag.feature.checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.usecase.CreateAddressUseCase
import com.example.price_tag.feature.checkout.domain.usecase.GetAddressesUseCase
import com.example.price_tag.feature.checkout.presentation.mapper.isValid
import com.example.price_tag.feature.checkout.presentation.mapper.toNewAddress
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState
import com.example.utilites.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val getAllAddressesUseCase: GetAddressesUseCase,
    private val createAddressUseCase: CreateAddressUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CheckoutContract.CheckoutState())
    val state: StateFlow<CheckoutContract.CheckoutState> = _state.asStateFlow()

    private var cartJob: Job? = null
    private var addressJob: Job? = null
    private var createAddressJob: Job? = null

    fun onIntent(intent: CheckoutContract.CheckoutIntent) {
        when (intent) {
            is CheckoutContract.CheckoutIntent.LoadCheckout -> {
                loadCheckout(intent.cartId)

                if (intent.cartId > 0L) {
                    loadAddresses()
                }
            }

            is CheckoutContract.CheckoutIntent.SelectAddress -> {
                selectAddress(addressId = intent.address.id)
            }

            is CheckoutContract.CheckoutIntent.AddAddressClicked -> {
                addAddressClicked()
            }

            is CheckoutContract.CheckoutIntent.GetAddresses -> {
                loadAddresses()
            }

            is CheckoutContract.CheckoutIntent.RecipientNameChanged -> {
                _state.update { currentState -> currentState.copy(recipientName = intent.recipientName) }
            }

            is CheckoutContract.CheckoutIntent.PhoneNumberChanged -> {
                _state.update { currentState -> currentState.copy(phoneNumber = intent.phoneNumber) }
            }

            is CheckoutContract.CheckoutIntent.AddressFormChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        addressForm = intent.addressForm,
                        createAddressState = ResourceState.Initial,
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.DismissAddressForm -> {
                _state.update { currentState ->
                    currentState.copy(
                        isAddressFormVisible = false,
                        addressForm = AddressFormState(),
                        createAddressState = ResourceState.Initial,
                    )
                }
            }

            is CheckoutContract.CheckoutIntent.SubmitAddressForm -> {
                submitAddressForm()
            }

            else -> Unit
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
                currentState.copy(selectedAddressId = addressId)
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
        val addressForm: AddressFormState = _state.value.addressForm
        if (!_state.value.addressForm.isValid()) {
            return
        }

        val newAddress: NewAddress = addressForm.toNewAddress()
        if (createAddressJob?.isActive == true) {
            return
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
                            )
                        }

                        else -> currentState.copy(createAddressState = response)
                    }
                }
            }
        }
    }


}