package com.myprojects.qrscanner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography configuration for the QR Scanner application.
 * 
 * This file defines the text styles used throughout the application.
 * The typography follows Material Design 3 guidelines and provides
 * consistent text styling across all screens.
 * 
 * The typography includes:
 * - Body text styles for general content
 * - Title styles for headings and important text
 * - Label styles for small text elements
 * 
 * @author Edouard Makon
 * @since 1.0
 */

/**
 * Material 3 typography configuration for the application.
 * 
 * This Typography object defines the text styles used throughout the app.
 * It follows Material Design 3 guidelines and provides a consistent
 * visual hierarchy for text elements.
 * 
 * Current configuration includes:
 * - bodyLarge: Default body text style with 16sp font size
 * - Additional styles can be added as needed for titles, labels, etc.
 */
val Typography = Typography(
    /**
     * Large body text style for main content.
     * 
     * Used for paragraphs and general text content throughout the application.
     * Features:
     * - Font size: 16sp
     * - Line height: 24sp
     * - Letter spacing: 0.5sp
     * - Font weight: Normal
     */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)