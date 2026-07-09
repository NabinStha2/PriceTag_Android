package com.example.price_tag.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryColor100,
    onPrimaryContainer = PrimaryColor900,

    secondary = AccentColor,
    onSecondary = Color.White,
    secondaryContainer = AccentColor100,
    onSecondaryContainer = AccentColor900,

    tertiary = AccentColor,

    background = NeutralColor50,
    onBackground = NeutralColor900,

    surface = NeutralColor50,
    onSurface = NeutralColor900,
    surfaceVariant = NeutralColor100,
    onSurfaceVariant = NeutralColor600,

    error = ErrorColor,
    onError = Color.White,
    errorContainer = ErrorColor100,
    onErrorContainer = ErrorColor900,

    outline = NeutralColor300,
    outlineVariant = NeutralColor200,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor400,
    onPrimary = PrimaryColor900,
    secondary = AccentColor400,
    background = NeutralColor950,
    onBackground = NeutralColor100,
    surface = NeutralColor900,
    onSurface = NeutralColor100,
    error = ErrorColor400,
    onError = ErrorColor900,
)


@Composable
fun PriceTagTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                  dynamicColor: Boolean = false, // set true if you want Android 12+ wallpaper-based colors
                  content: @Composable () -> Unit) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window,
                                             view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PoppinsTypography, // <-- this is the important line, links your font setup
        content = content,
    )
}