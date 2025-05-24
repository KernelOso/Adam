package com.kerneloso.adam.ui.view.window

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.domain.model.Seller
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.viewmodel.SellersViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun sellersFormWindow(
    seller: Seller? = null,
    onClose: () -> Unit,
    viewmodel: SellersViewModel
) {
    //Form Variables
    var sellerID by remember { mutableStateOf(0L) }
    var sellerName by remember { mutableStateOf("") }

    //Form type trigger
    var showEditButtons by remember { mutableStateOf(false) }

    //Set title and import object
    val title : String
    if (seller == null){
        title = stringResource(Res.string.sellersFormWindow_windowTitle_New)
    } else {
        title = stringResource(Res.string.sellersFormWindow_windowTitle_Edit)

        sellerID = seller.id
        sellerName = seller.name

        showEditButtons = true
    }

    //Form Verifications
    var showNameEmptyScreen by remember { mutableStateOf(false) }

    Window(
        onCloseRequest = { onClose() },
        title = title,
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
                        text = "ID : $sellerID",
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
                    value = sellerName,
                    onValueChange = { sellerName = it },
                    label = stringResource(Res.string.sellersFormWindow_formField_productName),
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
                        text = stringResource(Res.string.sellersFormWindow_button_update),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (seller != null) {
                                    verifyFormData(
                                        nameValue = sellerName,
                                        showNameEmptyView = {showNameEmptyScreen = it},
                                        content = {
                                            viewmodel.updateSeller(
                                                Seller(
                                                    id = sellerID,
                                                    name = sellerName,
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
                        text = stringResource(Res.string.sellersFormWindow_button_delete),
                        backgroundColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (seller != null) {
                                    viewmodel.deleteSeller(seller)
                                }
                                onClose()
                            }
                    )
                    // =============================================================================
                } else {
                    // =============================================================================
                    // Button : Create
                    simpleButton(
                        text = stringResource(Res.string.sellersFormWindow_button_new),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                verifyFormData(
                                    nameValue = sellerName,
                                    showNameEmptyView = {showNameEmptyScreen = it},
                                    content = {
                                        viewmodel.addSeller(
                                            Seller(
                                                id = viewmodel.sellersDB.value.lastID + 1,
                                                name = sellerName,
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


        if (showNameEmptyScreen){
            showWindowNotification(
                title = stringResource(Res.string.sellersFormWindow_notification_noName_title),
                text = stringResource(Res.string.sellersFormWindow_notification_noName_content),
                onClose = { showNameEmptyScreen = false }
            )
        }

    }
}

private fun verifyFormData (
    nameValue: String = "",
    content: () -> Unit,
    showNameEmptyView: (Boolean) -> Unit,
) {
    //Verify content
    var nameIsEmpty = nameValue.isEmpty()

    //Show Screens
    showNameEmptyView(nameIsEmpty)

    //Exec code
    if ( !nameIsEmpty ) {
        content()
    }
}