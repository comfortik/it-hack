package com.example.it_tech_hack.ui.theme

import android.app.Activity
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

val DarkGray = Color(0xFF262525)
val LightGray = Color(0xFF9C27B0)
val MonospaceTextColor = Color(0xFFFFFFFF)

val DarkChromaColorScheme = darkColorScheme(
    primary = ChromaBackground,
    onPrimary = ChromaOnPrimary,
    secondary = ChromaSecondary,
    onSecondary = ChromaOnSecondary,
    background = ChromaBackground,
    onBackground = ChromaOnBackground,
    surface = ChromaSurface,
    onSurface = ChromaOnSurface,
    error = ChromaError,
    onError = ChromaOnBackground
)
val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = LightGray,
    tertiary = LightGray,
    background = Color.White,
    surface = Color.White,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onBackground = DarkGray,
    onSurface = DarkGray
)



@Composable
fun IttechhackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkChromaColorScheme
        else -> DarkChromaColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}