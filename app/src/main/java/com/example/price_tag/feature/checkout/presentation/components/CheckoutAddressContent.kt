package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.price_tag.feature.checkout.domain.model.Address
import com.example.utilites.ResourceState

@Composable
fun CheckoutAddressContent(
    addressesState: ResourceState<List<Address>>,
    selectedAddressId: Long?,
    onAddressSelected: (Address) -> Unit,
    onAddAddressClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    selectedAddressError: String? = null,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        when (addressesState) {
            ResourceState.Initial, ResourceState.Loading -> {
                Text(
                    text = "Delivery address",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = "Loading saved addresses…",
                    style = MaterialTheme.typography.bodyMedium,
                )

                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            is ResourceState.Error -> {
                Text(
                    text = "Delivery address",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = addressesState.error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )

                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Retry")
                }

                TextButton(onClick = onAddAddressClick) {
                    Text(text = "Add address")
                }
            }

            is ResourceState.Success -> {
                CheckoutAddressSection(
                    addresses = addressesState.data,
                    selectedAddressId = selectedAddressId,
                    onAddressSelected = onAddressSelected,
                    onAddAddressClick = onAddAddressClick,
                    enabled = enabled,
                )
            }
        }

        selectedAddressError?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}