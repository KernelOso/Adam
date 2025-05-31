package com.kerneloso.adam.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.onClick
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun loadingCircle(
    trigger: Boolean = false,
    ) {
    if (trigger) {
        viewWhitLogoBackground {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().onClick{}
            ){
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(0.4f),
                    strokeWidth = 20.dp
                )
            }
        }
    }
}