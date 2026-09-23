package com.example.price_tag.feature.order.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsResponseDto(
    val orderId: Long,
    val orderNumber: String,
    val status: String,
    val paymentStatus: String,
    val subtotalAmt: Double,
    val discountAmt: Double,
    val shippingAmt: Double,
    val taxAmt: Double,
    val totalAmt: Double,
    val currency: String,
    val shippingAddress: OrderShippingAddressResponseDto,
    val items: List<OrderItemResponseDto>,
    val trackingNumber: String? = null,
    val cancellationReason: String? = null,
    val paidAt: String? = null,
    val createdAt: String? = null,
    val expiresAt: String? = null,
)
