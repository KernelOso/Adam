package com.kerneloso.adam.ui.view.window

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.productFormWindow_form_button_deleteProduct
import adam.composeapp.generated.resources.productFormWindow_form_button_newProduct
import adam.composeapp.generated.resources.productFormWindow_form_button_updateProduct
import adam.composeapp.generated.resources.productFormWindow_form_productName
import adam.composeapp.generated.resources.productFormWindow_form_productPrice
import adam.composeapp.generated.resources.productFormWindow_form_productType
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
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.ui.component.formDropdownMenu
import com.kerneloso.adam.ui.component.formPriceTextField
import com.kerneloso.adam.ui.component.formTextField
import com.kerneloso.adam.ui.component.showWindowNotification
import com.kerneloso.adam.ui.component.simpleButton
import com.kerneloso.adam.ui.component.viewWhitLogoBackground
import com.kerneloso.adam.ui.viewmodel.LensViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun productFormWindow(
    lens: Lens? = null,
    onClose: () -> Unit,
    viewmodel: LensViewModel
) {
    //Form Variables
    var lensID by remember { mutableStateOf(0L) }
    var lensName by remember { mutableStateOf("") }
    var lensPrice by remember { mutableStateOf(0L) }

    //Form type trigger
    var showEditButtons by remember { mutableStateOf(false) }

    //Set title and import object
    val title : String
    if (lens == null){
        title = stringResource(Res.string.productFormWindow_typeNew)
    } else {
        title = stringResource(Res.string.productFormWindow_typeEdit)

        lensID = lens.id
        lensName = lens.name
        lensPrice = lens.price

        showEditButtons = true
    }

    //Form Verifications
    var showNameEmptyScreen by remember { mutableStateOf(false) }
    var showPriceEmptyScreen by remember { mutableStateOf(false) }

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
                        text = "ID : $lensID",
                    )
                } else {
                    Box(
                        modifier = Modifier.height(20.dp),
                    )
                }
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                // Text Field : Product Name
                formTextField(
                    value = lensName,
                    onValueChange = { lensName = it },
                    label = stringResource(Res.string.productFormWindow_form_productName),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                // Text Field : Product Price
                formPriceTextField(
                    initialValue = lensPrice,
                    label = stringResource(Res.string.productFormWindow_form_productPrice),
                    onPriceChange = { lensPrice = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                if ( showEditButtons ) {
                    // =============================================================================
                    // Button : Update
                    simpleButton(
                        text = stringResource(Res.string.productFormWindow_form_button_updateProduct),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (lens != null) {
                                    verifyFormData(
                                        nameValue = lensName,
                                        priceValue = lensPrice,
                                        showNameEmptyView = {showNameEmptyScreen = it},
                                        showPriceEmptyView = { showPriceEmptyScreen = it },
                                        content = {
                                            viewmodel.updateLens(
                                                Lens(
                                                    id = lensID,
                                                    name = lensName,
                                                    price = lensPrice
                                                )
                                            )
                                        }
                                    )
                                }
                                onClose()
                            }
                    )
                    // =============================================================================
                    Spacer(modifier = Modifier.height(20.dp))
                    // =============================================================================
                    // Button : Delete
                    simpleButton(
                        text = stringResource(Res.string.productFormWindow_form_button_deleteProduct),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (lens != null) {
                                    viewmodel.deleteLens(lens)
                                }
                                onClose()
                            }
                    )
                    // =============================================================================
                } else {
                    // =============================================================================
                    // Button : Create
                    simpleButton(
                        text = stringResource(Res.string.productFormWindow_form_button_newProduct),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                verifyFormData(
                                    nameValue = lensName,
                                    priceValue = lensPrice,
                                    showNameEmptyView = {showNameEmptyScreen = it},
                                    showPriceEmptyView = { showPriceEmptyScreen = it },
                                    content = {
                                        viewmodel.addLens(
                                            Lens(
                                                id = viewmodel.lensDB.value.lastID + 1,
                                                name = lensName,
                                                price = lensPrice
                                            )
                                        )
                                        onClose()
                                    }
                                )
                            }
                    )
                }
            }
        }

        //Screen Verifications
        if (showPriceEmptyScreen){
            showWindowNotification(
                title = "Price is 0",
                text = "El precio del producto no puede ser 0",
                onClose = { showPriceEmptyScreen = false }
            )
        }

        if (showNameEmptyScreen){
            showWindowNotification(
                title = "Name is Empty",

                text = "El nombre del producto no puede estar vacio",
                onClose = { showNameEmptyScreen = false }
            )
        }

    }
}



fun verifyFormData (
    nameValue: String = "",
    priceValue: Long = 0L,
    content: () -> Unit,
    showNameEmptyView: (Boolean) -> Unit,
    showPriceEmptyView: (Boolean) -> Unit,
) {
    //Verify content
    var nameIsEmpty = nameValue.isEmpty()
    var priceIsZero = (priceValue == 0L)

    // form some reason, if show both screen, it crash
    if ( nameIsEmpty && priceIsZero ){
       priceIsZero = false
    }

    //Show Screens
    showNameEmptyView(nameIsEmpty)
    showPriceEmptyView(priceIsZero)

    //Exec code
    if ( !nameIsEmpty && !priceIsZero ) {
        content()
    }
}