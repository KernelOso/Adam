package com.kerneloso.adam.ui.window

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.productFormWindow_form_productName
import adam.composeapp.generated.resources.productFormWindow_form_productPrice
import adam.composeapp.generated.resources.productFormWindow_typeEdit
import adam.composeapp.generated.resources.productFormWindow_typeNew
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.ui.components.formDropdownMenu
import com.kerneloso.adam.ui.components.formPriceTextField
import com.kerneloso.adam.ui.components.formTextField
import com.kerneloso.adam.ui.components.simpleButton
import com.kerneloso.adam.ui.components.viewWhitLogoBackground
import com.kerneloso.adam.ui.type.FormType
import com.kerneloso.adam.ui.viewmodel.ProductViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun productFormWindow(
    product: Product? = null,
    onClose: () -> Unit,
    viewmodel: ProductViewModel
) {
    var productId by remember { mutableStateOf(0L) }
    var productName by remember { mutableStateOf("") }
    var productType by remember { mutableStateOf(viewmodel.productDB.value.ProductsTypes[0]) }
    var productPrice by remember { mutableStateOf(0L) }

    var showEditButtons by remember { mutableStateOf(false) }

    val title : String
    if (product == null){
        title = stringResource(Res.string.productFormWindow_typeNew)
    } else {
        title = stringResource(Res.string.productFormWindow_typeEdit)

        productId = product.productId
        productName = product.productName
        productType = product.productType
        productPrice = product.productPrice

        showEditButtons = true
    }

    Window(
        onCloseRequest = { onClose() },
        title = title,
        resizable = false,
        state = WindowState(),
        alwaysOnTop = true
    ) {
        val winWidth = 400
        val windHeight = 600
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }
        
        viewWhitLogoBackground {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // =================================================================================
                if ( showEditButtons ) {
                    Text(
                        modifier = Modifier.height(20.dp),
                        text = "ID : $productId",
                    )
                } else {
                    Box(
                        modifier = Modifier.height(20.dp),
                    )
                }
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                formTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = stringResource(Res.string.productFormWindow_form_productName),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                formDropdownMenu(
                    prefix = "Tipo de Producto : ",
                    options = viewmodel.productDB.value.ProductsTypes,
                    onOptionSelected = { productType = it },
                    defaultOption = productType,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                formPriceTextField(
                    initialValue = productPrice,
                    label = stringResource(Res.string.productFormWindow_form_productPrice),
                    onPriceChange = { productPrice = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                if ( showEditButtons ) {
                    simpleButton(
                        text = "Actualizar Producto",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (product != null) {
                                    viewmodel.updateProduct(
                                        Product(
                                            productId = productId,
                                            productName = productName,
                                            productType = productType,
                                            productPrice = productPrice
                                        )
                                    )
                                }
                                onClose()
                            }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    simpleButton(
                        text = "Eliminar Producto",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (product != null) {
                                    viewmodel.deleteProduct(product)
                                }
                                onClose()
                            }
                    )

                } else {
                    simpleButton(
                        text = "Agregar Producto",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                viewmodel.addProduct(
                                    Product(
                                        productId = viewmodel.productDB.value.lastProductId + 1,
                                        productName = productName,
                                        productType = productType,
                                        productPrice = productPrice
                                    )
                                )
                                onClose()
                            }
                    )
                }
            }
        }
    }
}