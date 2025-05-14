package com.kerneloso.adam

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kerneloso.adam.ui.state.WindowStateHolder
import com.kerneloso.adam.ui.theme.Theme

val windowWidth = 800.dp
val windowHeight = 600.dp

fun main() = application {

    val actualWindowState = WindowStateHolder.windowState
    WindowStateHolder.ChangeWindowSize( x = windowWidth , y = windowHeight )

    Window(
        undecorated = false,
        state = actualWindowState,
        onCloseRequest = ::exitApplication,
        title = "Adam",
        resizable = false
    ) {
        Theme {
            App()
        }
    }
}