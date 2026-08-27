package com.example.price_tag.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryColor100,
    onPrimaryContainer = PrimaryColor900,
    inversePrimary = PrimaryColor300,

    secondary = AccentColor700,
    onSecondary = Color.White,
    secondaryContainer = AccentColor100,
    onSecondaryContainer = AccentColor900,

    tertiary = PrimaryColor700,
    onTertiary = Color.White,
    tertiaryContainer = PrimaryColor50,
    onTertiaryContainer = PrimaryColor900,

    background = NeutralColor50,
    onBackground = NeutralColor900,
    surface = Color.White,
    onSurface = NeutralColor900,
    surfaceVariant = NeutralColor100,
    onSurfaceVariant = NeutralColor600,
    surfaceTint = PrimaryColor,

    inverseSurface = NeutralColor900,
    inverseOnSurface = NeutralColor100,
    surfaceDim = NeutralColor200,
    surfaceBright = Color.White,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = NeutralColor50,
    surfaceContainer = NeutralColor100,
    surfaceContainerHigh = NeutralColor100,
    surfaceContainerHighest = NeutralColor200,

    error = ErrorColor,
    onError = Color.White,
    errorContainer = ErrorColor100,
    onErrorContainer = ErrorColor900,

    outline = NeutralColor400,
    outlineVariant = NeutralColor200,
    scrim = Color.Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor300,
    onPrimary = PrimaryColor950,
    primaryContainer = PrimaryColor800,
    onPrimaryContainer = PrimaryColor100,
    inversePrimary = PrimaryColor600,

    secondary = AccentColor300,
    onSecondary = AccentColor950,
    secondaryContainer = AccentColor800,
    onSecondaryContainer = AccentColor100,

    tertiary = PrimaryColor200,
    onTertiary = PrimaryColor950,
    tertiaryContainer = PrimaryColor900,
    onTertiaryContainer = PrimaryColor100,

    background = NeutralColor950,
    onBackground = NeutralColor100,
    surface = NeutralColor900,
    onSurface = NeutralColor100,
    surfaceVariant = NeutralColor800,
    onSurfaceVariant = NeutralColor300,
    surfaceTint = PrimaryColor300,

    inverseSurface = NeutralColor100,
    inverseOnSurface = NeutralColor900,
    surfaceDim = NeutralColor950,
    surfaceBright = NeutralColor800,
    surfaceContainerLowest = NeutralColor950,
    surfaceContainerLow = NeutralColor900,
    surfaceContainer = NeutralColor900,
    surfaceContainerHigh = NeutralColor800,
    surfaceContainerHighest = NeutralColor700,

    error = ErrorColor300,
    onError = ErrorColor950,
    errorContainer = ErrorColor800,
    onErrorContainer = ErrorColor100,

    outline = NeutralColor500,
    outlineVariant = NeutralColor700,
    scrim = Color.Black,
)

@Composable
fun PriceTagTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PoppinsTypography,
        content = content,
    )
}
