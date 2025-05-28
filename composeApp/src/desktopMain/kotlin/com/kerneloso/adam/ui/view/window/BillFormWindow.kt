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
                        .fillMaxWidth(0.5f)
                        .padding(20.dp)
                        .weight(9f)
                ) {
                    // =================================================================================
                    Spacer(modifier = Modifier.height(20.dp))
                    // =================================================================================
                    Text(
                        text = "=== === Info Basica === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ID : ${bill.id}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Fecha : ${bill.date}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Info Cliente === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Nombre del cliente : ${bill.clientName}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ID del cliente : ${bill.clientId}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Telefono del cliente : ${bill.clientNumber}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === === === === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Vendedor : ${bill.seller.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Info Lentes === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Tipo de lente : ${bill.lens.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "    - Precio : ${longToPrice(bill.lens.price)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Montura : ${bill.frame.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "    - Precio : ${longToPrice(bill.frame.price)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Ojo Izquierdo === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ESF : ${bill.oiESF}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "CIL : ${bill.oiCIL}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ADD : ${bill.oiADD}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "EJE : ${bill.oiEJE}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Ojo Derecho === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ESF : ${bill.odESF}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "CIL : ${bill.odCIL}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "ADD : ${bill.odADD}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "EJE : ${bill.odEJE}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Productos === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    bill.products.forEach { product ->

                        Text(
                            text = "    Nombre : ${product.product.name}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "    Precio UND : ${product.product.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "        Cantidad : ${product.quantity}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "    Precio total : ${product.quantity * product.product.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        // =================================================================================
                        Spacer(modifier = Modifier.height(10.dp))
                        // =================================================================================
                    }
                    Text(
                        text = "=== === Info Extra === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Color : ${bill.color}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "DP : ${bill.dp}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "=== === Total === ===",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "TOTAL : ${bill.total}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )


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
                    text = stringResource(Res.string.lensFormWindow_button_update),
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
                    text = "abrir pdf",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {
                            viewmodel.openPdf(bill)
                        }
                )
            }



        }
    }
}
