package com.cafq.lab08

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta "taller de motos": asfalto oscuro + naranja de escape
private val MotoColors = darkColorScheme(
    primary = Color(0xFFFF6B00),
    onPrimary = Color(0xFF1A1A1A),
    secondary = Color(0xFFFFB27A),
    background = Color(0xFF121212),
    onBackground = Color(0xFFF2F2F2),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFF2F2F2),
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFBDBDBD),
    error = Color(0xFFFF5252)
)

@Composable
fun MotoTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = MotoColors, content = content)
}
