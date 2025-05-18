package com.kerneloso.adam

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.app_name
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kerneloso.adam.io.FileUtil
import com.kerneloso.adam.ui.state.WindowStateHolder
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension


fun main() = application {

    val windowState = WindowStateHolder.windowState

    Window(
        undecorated = false,
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        resizable = true,
    ) {
        window.minimumSize = Dimension(
            600,
            600
        )
        App()
    }

}