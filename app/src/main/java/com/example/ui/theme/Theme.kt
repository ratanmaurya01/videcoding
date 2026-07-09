package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = VibePrimary,
  secondary = VibeSecondary,
  tertiary = VibeTertiary,
  background = VibeBackground,
  surface = VibeCardSurface,
  surfaceVariant = VibeSurfaceVariant,
  onPrimary = VibeOnPrimary,
  onBackground = VibeOnBackground,
  onSurface = VibeOnSurface,
  onSurfaceVariant = VibeOnBackground
)

private val LightColorScheme = lightColorScheme(
  primary = Color(0xFF10B981),
  secondary = VibeSecondary,
  tertiary = VibeTertiary,
  background = Color(0xFFF8FAFC),
  surface = Color(0xFFFFFFFF),
  surfaceVariant = Color(0xFFE2E8F0),
  onPrimary = Color.White,
  onBackground = Color(0xFF0F172A),
  onSurface = Color(0xFF0F172A),
)

@Composable
fun VibeTradingTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

