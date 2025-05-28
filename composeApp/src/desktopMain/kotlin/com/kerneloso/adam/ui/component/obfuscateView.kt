package com.kerneloso.adam.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun obfuscateView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy( alpha = 0.8f ))
            .clickable {  }
    )
}