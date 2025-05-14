package com.kerneloso.adam


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kerneloso.adam.ui.state.WindowStateHolder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
    Box(
        modifier =  Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
    ) {

        Box(
            modifier =  Modifier
                        .size(200.dp)
                        .background(Color.Cyan)
                        .onClick {
                            WindowStateHolder.ChangeWindowSize( x = 1000.dp , y = 200.dp )
                        }
        )

    }
}