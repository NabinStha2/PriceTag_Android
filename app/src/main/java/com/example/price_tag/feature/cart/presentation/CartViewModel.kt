package com.example.price_tag.feature.cart.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    fun onIntent(cartIntent: CartContract.CartIntent) {
        when (cartIntent) {
            is CartContract.CartIntent.GetAllCartItems -> {

            }
        }
    }

    private fun getAllCartItems() {

    }
}