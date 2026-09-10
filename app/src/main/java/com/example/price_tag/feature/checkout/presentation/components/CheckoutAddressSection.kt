package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.PriceTagTheme
import com.example.price_tag.feature.checkout.domain.model.Address


@Composable
fun CheckoutAddressSection(
    addresses: List<Address>,
    selectedAddressId: Long?,
    onAddressSelected: (Address) -> Unit,
    onAddAddressClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "Delivery address",
            style = MaterialTheme.typography.titleLarge,
        )

        if (addresses.isEmpty()) {
            Text(
                text = "No saved addresses. Add a delivery address to continue.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                addresses.forEach { address ->
                    key(address.id) {
                        CheckoutAddressCard(
                            address = address,
                            isSelected = address.id == selectedAddressId,
                            onSelect = {
                                onAddressSelected(address)
                            },
                            enabled = enabled,
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onAddAddressClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Add address")
        }
    }
}

@Preview(
    name = "Delivery addresses",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun CheckoutAddressSectionPreview() {
    val address = Address(
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
    )

    PriceTagTheme {
        CheckoutAddressSection(
            addresses = listOf(address),
            selectedAddressId = address.id,
            onAddressSelected = {},
            onAddAddressClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(
    name = "No delivery addresses",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun CheckoutAddressSectionEmptyPreview() {
    PriceTagTheme {
        CheckoutAddressSection(
            addresses = emptyList(),
            selectedAddressId = null,
            onAddressSelected = {},
            onAddAddressClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}