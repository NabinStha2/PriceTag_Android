package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.price_tag.feature.checkout.domain.model.CreatedOrder
import com.example.price_tag.feature.checkout.domain.model.OrderStatus
import com.example.utilites.ResourceState

@Composable
fun CheckoutOrderStatus(
    orderState: ResourceState<CreatedOrder>,
    modifier: Modifier = Modifier,
) {
    when (orderState) {
        is ResourceState.Error -> {
            Text(
                text = orderState.error,
                modifier = modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        is ResourceState.Success -> {
            val order = orderState.data

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Order ${order.orderNumber}",
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = if (order.status == OrderStatus.PENDING_PAYMENT) {
                        "Your order has been created. Payment is still required ."
                    } else {
                        "Status: ${
                            order.status.name.replace('_',
                                                      ' ')
                        }"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        else -> Unit
    }
}