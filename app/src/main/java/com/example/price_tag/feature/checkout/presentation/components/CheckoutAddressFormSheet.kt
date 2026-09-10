package com.example.price_tag.feature.checkout.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.price_tag.feature.checkout.presentation.model.AddressFormErrors
import com.example.price_tag.feature.checkout.presentation.model.AddressFormState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutAddressFormSheet(
    form: AddressFormState,
    isSaving: Boolean,
    onFormChange: (AddressFormState) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String?, // this errorMessage if from the API or network failures
    errors: AddressFormErrors,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                bottom = 32.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    text = "Add delivery address",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            if (errorMessage != null) {
                item {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            item {
                AddressTextField(
                    value = form.label,
                    label = "Label (for example, Home)",
                    onValueChange = { onFormChange(form.copy(label = it)) },
                    errorMessage = errors.label,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.unitNumber,
                    label = "Unit / apartment number",
                    onValueChange = {
                        onFormChange(form.copy(unitNumber = it))
                    },
                    errorMessage = errors.unitNumber,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.streetNumber,
                    label = "Street number",
                    onValueChange = {
                        onFormChange(form.copy(streetNumber = it))
                    },
                    errorMessage = errors.streetNumber,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.street,
                    label = "Street *",
                    onValueChange = { onFormChange(form.copy(street = it)) },
                    errorMessage = errors.street,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.city,
                    label = "City *",
                    onValueChange = { onFormChange(form.copy(city = it)) },
                    errorMessage = errors.city,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.state,
                    label = "State *",
                    onValueChange = { onFormChange(form.copy(state = it)) },
                    errorMessage = errors.state,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.country,
                    label = "Country *",
                    onValueChange = { onFormChange(form.copy(country = it)) },
                    errorMessage = errors.country,
                    enabled = !isSaving,
                )
            }

            item {
                AddressTextField(
                    value = form.postalCode,
                    label = "Postal code *",
                    onValueChange = {
                        onFormChange(form.copy(postalCode = it))
                    },
                    errorMessage = errors.postalCode,
                    enabled = !isSaving,
                )
            }

            item {
                Switch(
                    checked = form.isDefault,
                    onCheckedChange = {
                        onFormChange(form.copy(isDefault = it))
                    },
                    enabled = !isSaving,
                )
            }

            item {
                Text(
                    text = "Use as my default delivery address ",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    enabled = !isSaving,
                    onClick = onSave,
                ) {
                    Text(if (isSaving) "Saving..." else "Save address")
                }
            }
        }
    }
}

@Composable
private fun AddressTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    enabled: Boolean,
) {
    OutlinedTextField(
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
        ),
        isError = errorMessage.isNullOrBlank()
            .not(),
        supportingText = errorMessage?.let { message ->
            {
                Text(
                    message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
    )
}
