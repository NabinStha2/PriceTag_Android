package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.PriceTagTheme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@Composable
fun CheckoutOrderSummaryCard(
    itemCount: Long,
    subtotal: Double,
    shippingFee: Double?,
    total: Double,
    currencyCode: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Order summary",
                style = MaterialTheme.typography.titleLarge,
            )

            HorizontalDivider()

            SummaryRow(
                label = "Items ($itemCount)",
                value = formatCurrency(
                    subtotal,
                    currencyCode,
                ),
            )

            SummaryRow(
                label = "Shipping",
                value = when (shippingFee) {
                    null -> "Calculated later"
                    0.0 -> "Free"
                    else -> formatCurrency(
                        shippingFee,
                        currencyCode,
                    )
                },
            )

            HorizontalDivider()

            SummaryRow(
                label = if (shippingFee == null) "Estimated total" else "Total",
                value = formatCurrency(
                    total,
                    currencyCode,
                ),
                emphasized = true,
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    emphasized: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = if (emphasized) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            },
        )

        Text(
            text = value,
            style = if (emphasized) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.bodyMedium
            },
            fontWeight = if (emphasized) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            color = if (emphasized) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
        )
    }
}

private fun formatCurrency(
    amount: Double,
    currencyCode: String,
): String {
    val formatter = NumberFormat.getCurrencyInstance(
        Locale.forLanguageTag("en-AU"),
    )

    runCatching {
        formatter.currency = Currency.getInstance(
            currencyCode.uppercase(Locale.ROOT),
        )
    }

    return formatter.format(amount)
}

@Preview(showBackground = true)
@Composable
private fun CheckoutOrderSummaryCardPreview() {
    PriceTagTheme {
        CheckoutOrderSummaryCard(
            itemCount = 3,
            subtotal = 168.90,
            shippingFee = 0.0,
            total = 168.90,
            currencyCode = "AUD",
            modifier = Modifier.padding(16.dp),
        )
    }
}
