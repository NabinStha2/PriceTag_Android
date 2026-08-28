package com.example.price_tag.feature.checkout.data.mapper

import com.example.price_tag.feature.checkout.data.model.request.CreateAddressRequestDto
import com.example.price_tag.feature.checkout.data.model.request.CreateOrderRequestDto
import com.example.price_tag.feature.checkout.data.model.response.AddressResponseDto
import com.example.price_tag.feature.checkout.data.model.response.CreateOrderResponseDto
import com.example.price_tag.feature.checkout.data.model.response.PaymentIntentResponseDto
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.price_tag.feature.checkout.domain.model.CreateOrderParams
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.NewAddress
import com.example.price_tag.feature.checkout.domain.model.OrderStatus
import com.example.price_tag.feature.checkout.domain.model.PaymentIntentDetails

fun AddressResponseDto.toDomain(): Address {
    return Address(
        id = id,
        unitNumber = unitNumber,
        streetNumber = streetNumber,
        street = street,
        city = city,
        state = state,
        country = country,
        postalCode = postalCode,
        label = label,
        isDefault = isDefault,
    )
}

fun NewAddress.toDto(): CreateAddressRequestDto {
    return CreateAddressRequestDto(
        unitNumber = unitNumber,
        streetNumber = streetNumber,
        street = street,
        city = city,
        state = state,
        country = country,
        postalCode = postalCode,
        label = label,
        isDefault = isDefault,
    )
}

fun CreateOrderParams.toDto(): CreateOrderRequestDto {
    return CreateOrderRequestDto(
        addressId = addressId,
        recipientName = recipientName,
        phoneNumber = phoneNumber,
    )
}

fun CreateOrderResponseDto.toDomain(): CreatedOrder {
    return CreatedOrder(
        orderId = orderId,
        orderNumber = orderNumber,
        status = status.toOrderStatus(),
        totalAmount = totalAmount,
        currency = currency,
    )
}

fun PaymentIntentResponseDto.toDomain(): PaymentIntentDetails {
    return PaymentIntentDetails(
        clientSecret = clientSecret,
        paymentIntentId = paymentIntentId,
        orderNumber = orderNumber,
        amountInMinorUnits = amount,
        currency = currency,
        expiresAtIso = expiresAt,
    )
}

private fun String.toOrderStatus(): OrderStatus {
    return OrderStatus.entries.firstOrNull { orderStatus ->
        orderStatus.name.equals(this,
                                ignoreCase = true)
    } ?: OrderStatus.UNKNOWN
}
