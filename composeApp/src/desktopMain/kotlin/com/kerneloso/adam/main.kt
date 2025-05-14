package com.kerneloso.adam

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.kerneloso.adam.ui.Theme
import java.awt.Dimension
import java.awt.Toolkit

val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
val screenWidth = screenSize.width
val screenHeight = screenSize.height

val windowWidth = 800.dp
val windowHeight = 600.dp

val windowSize =    DpSize(
                            width = windowWidth,
                            height = windowHeight
                          )

val windowPosition =    WindowPosition(
                                        x = ( ( screenWidth/2 ).dp - (windowWidth/2) ) ,
                                        y = ( ( screenHeight/2 ).dp - (windowHeight/2) )
                                      )

val windowState =   WindowState(
                                size = windowSize,
                                position = windowPosition
                               )

fun main() = application {
    Window(
        undecorated = false,
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "Adam",
        resizable = false
    ) {
        Theme {
            App()
        }
    }
}