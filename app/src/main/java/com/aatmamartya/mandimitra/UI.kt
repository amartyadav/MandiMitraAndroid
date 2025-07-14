package com.aatmamartya.mandimitra

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val fontName = GoogleFont("Nunito")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

val mandiMitraColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color.White,
    secondary = Color(0xFFFF9800),
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFF44336),
    onError = Color.White
    // Define other colors if needed
)

// dark color scheme
val mandiMitraDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784), // A lighter shade of your primary green for better visibility in dark mode
    onPrimary = Color.Black, // Black text on the lighter primary color
    secondary = Color(0xFFFFB74D), // A lighter shade of your secondary orange for better visibility
    onSecondary = Color.Black, // Black text on the lighter secondary color
    background = Color(0xFF303030), // A dark grey for background
    onBackground = Color.White, // White text on dark background
    surface = Color(0xFF424242), // Slightly lighter grey than background for surfaces
    onSurface = Color.White, // White text on dark surfaces
    error = Color(0xFFE57373), // A lighter shade of your error color for better visibility in dark mode
    onError = Color.Black // Black text on the lighter error color
    // Add more custom colors if needed
)