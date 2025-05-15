package com.kerneloso.adam

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kerneloso.adam.ui.state.WindowStateHolder

//Set window Size
val windowWidth = 1080.dp
val windowHeight = 800.dp

fun main() = application {

    val windowState = WindowStateHolder.windowState
    WindowStateHolder.changeWindowSize( x = windowWidth , y = windowHeight )

    Window(
        undecorated = false,
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "Adam",
        resizable = true
    ) {
        App()
    }
}