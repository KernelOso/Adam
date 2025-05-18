package com.kerneloso.adam.util

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import java.awt.Dimension
import java.awt.Toolkit

fun resizeAndCenterWindow(
    window : ComposeWindow,
    with: Int,
    height : Int
){
    window.size = Dimension(with , height)
    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize

    window.setLocation(
        ((screenSize.width / 2) - (with / 2)) ,
        ((screenSize.height / 2) - (height / 2))
    )
}