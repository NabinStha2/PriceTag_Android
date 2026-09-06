package com.example.price_tag.feature.checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.feature.cart.domain.usecase.GetAllCartItemsUseCase
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
) : ViewModel() {
    private val _state = MutableStateFlow(CheckoutContract.CheckoutState())
    val state: StateFlow<CheckoutContract.CheckoutState> = _state.asStateFlow()

    private var cartJob: Job? = null

    fun onIntent(intent: CheckoutContract.CheckoutIntent) {
        when (intent) {
            is CheckoutContract.CheckoutIntent.LoadCheckout -> {
                loadCheckout(intent.cartId)
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
}