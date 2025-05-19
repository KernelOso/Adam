package com.kerneloso.adam.ui.window

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.productFormWindow_form_productName
import adam.composeapp.generated.resources.productFormWindow_form_productPrice
import adam.composeapp.generated.resources.productFormWindow_form_productType
import adam.composeapp.generated.resources.productFormWindow_typeEdit
import adam.composeapp.generated.resources.productFormWindow_typeNew
import adam.composeapp.generated.resources.tf_label_ClientName
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.constraintlayout.compose.ConstraintLayout
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.ui.components.formPriceTextField
import com.kerneloso.adam.ui.components.formTextField
import com.kerneloso.adam.ui.components.simpleButton
import com.kerneloso.adam.ui.components.viewWhitLogoBackground
import com.kerneloso.adam.ui.type.FormType
import com.kerneloso.adam.ui.viewmodel.ProductViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun productFormWindow(
    type: FormType = FormType.NEW,
    onClose: () -> Unit
) {

    val title = when (type){
        FormType.NEW -> stringResource(Res.string.productFormWindow_typeNew)
        FormType.EDIT -> stringResource(Res.string.productFormWindow_typeEdit)
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

            var productName by remember { mutableStateOf("") }
            var productType by remember { mutableStateOf("") }
            var productPrice by remember { mutableStateOf(0L) }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // =================================================================================
                Text(
                    text = "ID : ",
                )
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

                // TODO : cambiar por un menu desplegable


                formTextField(
                    value = productType,
                    onValueChange = { productType = it },
                    label = stringResource(Res.string.productFormWindow_form_productType),
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
                simpleButton(
                    text = "Agregar Producto",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .onClick {


                            exitProcess(0)
                        }

                )
            }



        }
    }

}