package com.example.price_tag.feature.cart.presentation.components

import java.text.NumberFormat
import java.util.Locale

internal fun formatCartPrice(amount: Double): String {
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-AU"))
        .format(amount)
}
