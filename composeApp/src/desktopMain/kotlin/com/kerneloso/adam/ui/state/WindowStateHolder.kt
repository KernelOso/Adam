package com.kerneloso.adam.ui.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import java.awt.Dimension
import java.awt.Toolkit

object WindowStateHolder {
    val windowState: WindowState = WindowState()

    fun changeWindowSize(x: Dp, y: Dp) {

        val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        val screenWidth = screenSize.width
        val screenHeight = screenSize.height

        windowState.size = DpSize(width = x, height = y)
        windowState.position = WindowPosition(
            x = ((screenWidth / 2).dp - (x / 2)),
            y = ((screenHeight / 2).dp - (y / 2)),
        )

    }
}