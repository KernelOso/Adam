package com.kerneloso.adam.ui.view.window

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
import com.kerneloso.adam.domain.model.Frame
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.viewmodel.FramesViewModel
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun frameFormWindow(
    frame: Frame? = null,
    onClose: () -> Unit,
    viewmodel: FramesViewModel
) {
    //Form Variables
    var frameID by remember { mutableStateOf(0L) }
    var frameName by remember { mutableStateOf("") }
    var framePrice by remember { mutableStateOf(0L) }

    //Form type trigger
    var showEditButtons by remember { mutableStateOf(false) }

    //Set title and import object
    val title : String
    if (frame == null){
        title = stringResource(Res.string.lensFormWindow_windowTitle_New)
    } else {
        title = stringResource(Res.string.lensFormWindow_windowTitle_Edit)

        frameID = frame.id
        frameName = frame.name
        framePrice = frame.price

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
                        text = "ID : $frameID",
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
                    value = frameName,
                    onValueChange = { frameName = it },
                    label = stringResource(Res.string.lensFormWindow_formField_lensName),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                // =================================================================================
                Spacer(modifier = Modifier.height(20.dp))
                // =================================================================================
                // Text Field : Product Price
                formPriceTextField(
                    initialValue = framePrice,
                    label = stringResource(Res.string.lensFormWindow_formField_lensPrice),
                    onPriceChange = { framePrice = it },
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
                        text = stringResource(Res.string.lensFormWindow_button_update),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (frame != null) {
                                    verifyFormData(
                                        nameValue = frameName,
                                        priceValue = framePrice,
                                        showNameEmptyView = {showNameEmptyScreen = it},
                                        showPriceEmptyView = { showPriceEmptyScreen = it },
                                        content = {
                                            viewmodel.updateFrame(
                                                Frame(
                                                    id = frameID,
                                                    name = frameName,
                                                    price = framePrice
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
                        text = stringResource(Res.string.lensFormWindow_button_delete),
                        backgroundColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                if (frame != null) {
                                    viewmodel.deleteFrame(frame)
                                }
                                onClose()
                            }
                    )
                    // =============================================================================
                } else {
                    // =============================================================================
                    // Button : Create
                    simpleButton(
                        text = stringResource(Res.string.lensFormWindow_button_new),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                            .onClick {
                                verifyFormData(
                                    nameValue = frameName,
                                    priceValue = framePrice,
                                    showNameEmptyView = {showNameEmptyScreen = it},
                                    showPriceEmptyView = { showPriceEmptyScreen = it },
                                    content = {
                                        viewmodel.addFrame(
                                            Frame(
                                                id = viewmodel.framesDB.value.lastID + 1,
                                                name = frameName,
                                                price = framePrice
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
                title = stringResource(Res.string.lensFormWindow_notification_noPrice_title),
                text = stringResource(Res.string.lensFormWindow_notification_noPrice_content),
                onClose = { showPriceEmptyScreen = false }
            )
        }

        if (showNameEmptyScreen){
            showWindowNotification(
                title = stringResource(Res.string.lensFormWindow_notification_noName_title),
                text = stringResource(Res.string.lensFormWindow_notification_noName_content),
                onClose = { showNameEmptyScreen = false }
            )
        }

    }
}

private fun verifyFormData (
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