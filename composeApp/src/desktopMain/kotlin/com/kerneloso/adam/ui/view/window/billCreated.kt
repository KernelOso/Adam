package com.kerneloso.adam.ui.view.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.viewmodel.RegistersViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow


@Composable
fun billCreated(
    bill: Bill,
    onClose: () -> Unit,
    viewmodel: RegistersViewModel
) {
    Window(
        onCloseRequest = { onClose() },
        title = "Factura creada exitosamente",
        resizable = false,
        state = WindowState(),
        alwaysOnTop = true
    ) {
        val winWidth = 400
        val windHeight = 500
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        viewWhitLogoBackground {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .background(Color.Red)

            ) {

            }
        }
    }
}
