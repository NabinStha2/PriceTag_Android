package com.example.price_tag.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset


fun slideInFromRight(): EnterTransition = slideIn(tween(200)) {
    IntOffset(it.width,
              0)
}

fun slideOutToLeft(): ExitTransition = slideOut(tween(200)) {
    IntOffset(-it.width,
              0)
}