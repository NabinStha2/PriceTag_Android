package com.example.price_tag.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.price_tag.R

val Poppins = FontFamily(
    Font(
        R.font.poppins_regular,
        FontWeight.Normal,
    ),
    Font(
        R.font.poppins_medium,
        FontWeight.Medium,
    ),
    Font(
        R.font.poppins_semibold,
        FontWeight.SemiBold,
    ),
    Font(
        R.font.poppins_bold,
        FontWeight.Bold,
    ),
)

/**
 * App typography built on top of Material3's default type scale.
 * We keep Material's default sizes / line heights / letter spacing
 * and only swap in the Poppins font family + matching weights.
 */
val PoppinsTypography: Typography = Typography().let { base ->
    Typography(
        displayLarge = base.displayLarge.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),
        displayMedium = base.displayMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),
        displaySmall = base.displaySmall.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),

        headlineLarge = base.headlineLarge.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
        ),
        headlineMedium = base.headlineMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
        ),
        headlineSmall = base.headlineSmall.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
        ),

        titleLarge = base.titleLarge.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),
        titleMedium = base.titleMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),
        titleSmall = base.titleSmall.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),

        bodyLarge = base.bodyLarge.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),
        bodyMedium = base.bodyMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),
        bodySmall = base.bodySmall.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
        ),

        labelLarge = base.labelLarge.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),
        labelMedium = base.labelMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),
        labelSmall = base.labelSmall.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
        ),
    )
}