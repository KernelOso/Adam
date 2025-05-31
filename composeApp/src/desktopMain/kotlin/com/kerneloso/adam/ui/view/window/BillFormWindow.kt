package com.kerneloso.adam.ui.view.window

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.viewmodel.LensViewModel
import com.kerneloso.adam.ui.viewmodel.RegistersViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun billFormWindow(
    bill: Bill,
    onClose: () -> Unit,
    viewmodel: RegistersViewModel
) {
    Window(
        onCloseRequest = { onClose() },
        title = "Factura",
        resizable = false,
        state = WindowState(),
        alwaysOnTop = true
    ) {
        val winWidth = 600
        val windHeight = 700
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        var tfAbono by remember { mutableStateOf(0L) }
        var tfSaldo by remember { mutableStateOf(0L) }
        LaunchedEffect(bill) {
            tfAbono = bill.abono
        }
        
        viewWhitLogoBackground {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                //Contenido
                val scrollState = rememberScrollState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxWidth(0.8f)
                        .padding(20.dp)
                        .weight(9f)
                ) {
                    // =================================================================================
                    Spacer(modifier = Modifier.height(20.dp))
                    // =================================================================================
                    Line("OPTICA MCA")
                    Line("=== === === === === ===")
                    Line("Fecha : ${bill.date}")
                    Line("Factura #${bill.id}")
                    Line("=== === === === === ===")
                    Line("Cliente : ${bill.clientName}")
                    Line("Cel : ${bill.clientNumber}")
                    Line("C.C : ${bill.clientId}")
                    Line("=== === === === === ===")
                    Line("Vendendor : ${bill.seller.name}")
                    Line("=== === === === === ===")
                    Line("Ojo Derecho :")
                    Line("ESF : ${bill.odESF}")
                    Line("CIL : ${bill.odCIL}")
                    Line("EJE : ${bill.odEJE}")
                    Line("ADD : ${bill.odADD}")
                    Line("")
                    Line("Ojo Izquierdo :")
                    Line("ESF : ${bill.oiESF}")
                    Line("CIL : ${bill.oiCIL}")
                    Line("EJE : ${bill.oiEJE}")
                    Line("ADD : ${bill.oiADD}")
                    Line("=== === === === === ===")
                    Line("Lente : ${bill.lens.name}")
                    Line("Precio : ${bill.lens.price}")
                    Line("=== === === === === ===")
                    Line("Montura : ${bill.frame.name}")
                    Line("Precio : ${bill.frame.price}")
                    Line("=== === === === === ===")
                    bill.products.forEach { product ->
                        Line("- | ${product.quantity} | ${product.product.name}")
                        Line("    - UND : ${longToPrice(product.product.price)}")
                        Line("    - ${longToPrice(product.quantity * product.product.price)}")
                        Line("")
                    }
                    Line("=== === === === === ===")
                    Line("DP : ${bill.dp}")
                    Line("Color : ${bill.color}")
                    Line("=== === === === === ===")
                    Line("TOTAL : ${bill.total}")
                    Line("")
                    Line("Abono : ${bill.abono}")
                    Line("Saldo : ${bill.saldo}")


                }
                //abono y saldo y guardar cambios
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {

                    formPriceTextField(
                        initialValue =  tfAbono,
                        label = stringResource(Res.string.sellScreen_formField_abono),
                        onPriceChange = { tfAbono = it },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    tfSaldo = bill.total - tfAbono
                    Text(
                        text = "${stringResource(Res.string.sellScreen_formField_saldo)}${longToPrice(tfSaldo)}",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,

                        modifier = Modifier.weight(1f)
                    )
                }
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                simpleButton(
                    text = stringResource(Res.string.billWindow_button_update),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            viewmodel.updateBill(
                                bill.copy(
                                    abono = tfAbono,
                                    saldo = tfSaldo
                                )
                            )
                            onClose()
                        }
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                simpleButton(
                    text = stringResource(Res.string.billWindow_button_open),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            viewmodel.openPdf(bill)
                        }
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                simpleButton(
                    text = stringResource(Res.string.billWindow_button_print),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            viewmodel.printBill(bill)
                        }
                )
            }



        }
    }
}

@Composable
private fun Line(text: String = "") {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth()
    )
}
