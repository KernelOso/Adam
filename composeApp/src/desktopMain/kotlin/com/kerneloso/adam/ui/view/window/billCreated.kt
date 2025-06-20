package com.kerneloso.adam.ui.view.window

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.viewmodel.BillsViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun billCreated(
    bill: Bill,
    onClose: () -> Unit,
    viewmodel: BillsViewModel
) {
    Window(
        onCloseRequest = { onClose() },
        title = "Factura creada exitosamente",
        resizable = false,
        state = WindowState(),
        alwaysOnTop = true
    ) {
        val winWidth = 400
        val windHeight = 400
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        val verticalSeparator = 30.dp

        viewWhitLogoBackground {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)

            ) {
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                Text(
                    text = "Factura creata exitosdamente!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(20.dp)
                )
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                //Open
                simpleButton(
                    text = "abrir pdf",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            viewmodel.openPdf(bill)
                        }
                )
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                //Open
                simpleButton(
                    text = "Imprimir factura",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            //TODO
                        }
                )
            }
        }
    }
}
