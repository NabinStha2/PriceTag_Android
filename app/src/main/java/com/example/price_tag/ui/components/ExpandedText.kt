package com.example.price_tag.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.price_tag.ui.theme.PrimaryColor

@Composable
fun ExpandableText(
    text: String,
    collapsedMaxLines: Int = 3
) {
    var expanded by remember { mutableStateOf(false) }
    var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(null) }

    val showMore = textLayoutResultState?.hasVisualOverflow == true

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState = it },
            style = MaterialTheme.typography.bodyMedium
        )

        if (showMore || expanded) {
            Text(
                text = if (expanded) "Show Less" else "Show More",
                color = PrimaryColor,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { expanded = !expanded }
            )
        }
    }
}



