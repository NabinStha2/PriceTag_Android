package com.example.price_tag.feature.checkout.domain.model

data class CreateOrderParams(
    val addressId: Long,
    val recipientName: String,
    val phoneNumber: String,
)

data class CreatedOrder(
    val orderId: Long,
    val orderNumber: String,
    val status: OrderStatus,
    val totalAmount: Double,
    val currency: String,
)

enum class OrderStatus {
    PENDING_PAYMENT, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, EXPIRED, REFUNDED, UNKNOWN,
}
