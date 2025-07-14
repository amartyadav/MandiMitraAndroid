package com.aatmamartya.mandimitra

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MandiMitraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Dynamically choose theme based on system theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) mandiMitraDarkColorScheme else mandiMitraColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography, // Define your typography based on your font family
        content = content
    )
}
