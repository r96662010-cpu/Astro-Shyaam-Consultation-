package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CosmicDarkColorScheme = darkColorScheme(
    primary = CelestialGold,
    onPrimary = VoidBlack,
    primaryContainer = SurfaceVariantCosmic,
    onPrimaryContainer = PaleGold,
    secondary = MysticAmethyst,
    onSecondary = Color.White,
    secondaryContainer = SurfaceVariantCosmic,
    onSecondaryContainer = StarlightLavender,
    tertiary = RadiantCyan,
    onTertiary = VoidBlack,
    background = DeepSpaceNavy,
    onBackground = StarDust,
    surface = SurfaceCosmic,
    onSurface = StarDust,
    surfaceVariant = SurfaceVariantCosmic,
    onSurfaceVariant = TextMutedCosmic,
    outline = BorderCelestial,
    error = ErrorRuby,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // We default to dark celestial theme for mystical atmosphere
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CosmicDarkColorScheme,
        typography = Typography,
        content = content
    )
}
