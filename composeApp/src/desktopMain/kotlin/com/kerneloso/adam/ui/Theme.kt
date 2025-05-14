package com.kerneloso.adam.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    background = background,
    onBackground = onBackground,
)

@Composable
fun Theme (
    content: @Composable () -> Unit
) {
    MaterialTheme (
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}