package com.kerneloso.adam


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kerneloso.adam.ui.Theme

@Composable
fun App() {
    Box(
        modifier =  Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = "Hola!"
        )
    }
}