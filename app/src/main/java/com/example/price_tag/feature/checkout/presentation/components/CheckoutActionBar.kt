package com.example.price_tag.feature.checkout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.price_tag.core.theme.PriceTagTheme

@Composable
fun CheckoutActionBar(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isLoading: Boolean,
    onContinueClick: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
        ) {
            Button(
                enabled = enabled && !isLoading,
                onClick = onContinueClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,

                        )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = if (isLoading) {
                        "Preparing payment…"
                    } else {
                        "Continue to payment"
                    },
                )
            }
        }
    }
}


@Preview(
    name = "Checkout action bar",
    showBackground = true,
    widthDp = 360,
)
@Composable
private fun CheckoutActionBarPreview() {
    PriceTagTheme {
        CheckoutActionBar(
            enabled = true,
            isLoading = true,
            onContinueClick = {},
        )
    }
}