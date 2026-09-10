package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.price_tag.feature.checkout.presentation.model.ContactDetailsErrors

@Composable
fun CheckoutContactDetailsSection(
    recipientName: String,
    phoneNumber: String,
    onRecipientNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    contactDetailsErrors: ContactDetailsErrors = ContactDetailsErrors(),
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "Contact details",
            style = MaterialTheme.typography.titleLarge,
        )

        OutlinedTextField(
            value = recipientName,
            onValueChange = onRecipientNameChange,
            label = {
                Text(text = "Recipient name")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            isError = contactDetailsErrors.recipientName != null,
            supportingText = contactDetailsErrors.recipientName?.let { message ->
                { Text(text = message) }
            },
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = {
                Text(text = "Phone number")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
            ),
            isError = contactDetailsErrors.phoneNumber != null,
            supportingText = contactDetailsErrors.phoneNumber?.let { message ->
                { Text(text = message) }
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
