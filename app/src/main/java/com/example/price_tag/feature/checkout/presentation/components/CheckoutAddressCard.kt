package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.feature.checkout.domain.model.Address

@Composable
fun CheckoutAddressCard(
    address: Address,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                role = Role.RadioButton,
                onClick = onSelect,
                enabled = enabled,
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            },
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            },
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                enabled = enabled,
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = address.label?.takeIf { it.isNotBlank() } ?: "Delivery address",
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = address.toDisplayText(),
                    style = MaterialTheme.typography.bodyMedium,
                )

                if (address.isDefault) {
                    Text(
                        text = "Default address",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

private fun Address.toDisplayText(): String {
    val streetLine = listOfNotNull(streetNumber,
                                   street).filter { it.isNotBlank() }
        .joinToString(" ")

    val cityLine = listOf(city,
                          state,
                          postalCode).filter { it.isNotBlank() }
        .joinToString(" ")

    return listOfNotNull(
        unitNumber?.takeIf { it.isNotBlank() }
            ?.let {
                "Unit $it"
            },
        streetLine,
        cityLine,
        country,
    ).filter { it.isNotBlank() }
        .joinToString("\n")
}


@Preview(
    name = "Selected delivery address",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun CheckoutAddressCardPreview() {
    PriceTagTheme {
        CheckoutAddressCard(
            address = Address(
                id = 1L,
                unitNumber = "4",
                streetNumber = "12",
                street = "Example Street",
                city = "Sydney",
                state = "NSW",
                country = "Australia",
                postalCode = "2000",
                label = "Home",
                isDefault = true,
            ),
            isSelected = false,
            onSelect = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}