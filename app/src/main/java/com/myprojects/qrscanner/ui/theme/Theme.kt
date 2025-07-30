package com.myprojects.qrscanner.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Dark theme color scheme for the QR Scanner application.
 * 
 * This color scheme defines the colors used when the app is in dark mode.
 * It uses purple and pink tones that provide good contrast and readability
 * in low-light environments.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Light theme color scheme for the QR Scanner application.
 * 
 * This color scheme defines the colors used when the app is in light mode.
 * It uses purple and pink tones that provide good contrast and readability
 * in well-lit environments.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * Main theme composable for the QR Scanner application.
 * 
 * This composable function sets up the Material 3 theme for the entire application.
 * It handles both light and dark themes, as well as dynamic color support for
 * Android 12+ devices.
 * 
 * The theme automatically adapts to:
 * - System dark/light theme preference
 * - Dynamic color support on Android 12+ (Material You)
 * - Fallback to static color schemes for older devices
 * 
 * @param darkTheme Whether to use dark theme. Defaults to system preference.
 * @param dynamicColor Whether to use dynamic colors (Android 12+). Defaults to true.
 * @param content The composable content to be themed.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
@Composable
fun QRScannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}