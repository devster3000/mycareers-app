package com.shcg.mycareers.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.ui.platform.LocalContext


private val LightScheme = lightColorScheme(
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF111111),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF111111),
    surfaceVariant = Color(0xFFF2EEF8),
    onSurfaceVariant = Color(0xFF6A6A6A),
    primary = Color(0xFF5B4BB7),
    onPrimary = Color(0xFFFFFFFF),
    outline = Color(0xFFD9D3F3)
)

private val DarkScheme = darkColorScheme(
    background = Color(0xFF0F0F14),
    onBackground = Color(0xFFEDEDF2),
    surface = Color(0xFF15151C),
    onSurface = Color(0xFFEDEDF2),
    surfaceVariant = Color(0xFF1D1D27),
    onSurfaceVariant = Color(0xFFB9B9C6),
    primary = Color(0xFFB8ABFF),
    onPrimary = Color(0xFF14141A),
    outline = Color(0xFF3A3550)
)

@Composable
fun MyCareersTheme(
    darkMode: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val scheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkMode) DarkScheme else LightScheme
    }

    MaterialTheme(
        colorScheme = scheme,
        content = content
    )
}
