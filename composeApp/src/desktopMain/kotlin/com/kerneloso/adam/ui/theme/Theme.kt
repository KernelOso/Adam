package com.kerneloso.adam.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

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