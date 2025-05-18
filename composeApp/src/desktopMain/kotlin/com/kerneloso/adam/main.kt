package com.kerneloso.adam

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.app_name
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.kerneloso.adam.ui.ComposeWindowHolder
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension

fun main() = application {

    Window(
        undecorated = false,
        state = WindowState(),
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        resizable = true,
    ) {
        window.minimumSize = Dimension(
            600,
            600
        )
        ComposeWindowHolder.window = window
        App()
    }

}