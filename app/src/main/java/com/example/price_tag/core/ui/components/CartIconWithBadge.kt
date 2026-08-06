package com.example.price_tag.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.price_tag.core.theme.AccentColor50
import com.example.price_tag.core.theme.AccentColor500

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartIconWithBadge(count: Int = 0,
                      onClick: () -> Unit = {}) {
    Box {
        IconButton(onClick = onClick,
                   modifier = Modifier
                       .clip(shape = CircleShape)
                       .background(color = AccentColor50)
                       .size(size = 40.dp)) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Shopping Cart",
            )
        }
        if (count > 0) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .offset(x = 2.dp,
                            y = (-6).dp)
                    .clip(shape = CircleShape)
                    .background(color = AccentColor500)
                    .align(alignment = Alignment.TopEnd),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (count > 99) "99+" else count.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun CartIconWithBadgePreview() {
    CartIconWithBadge(count = 5)
}
