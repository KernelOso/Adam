package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.Roboto_Bold
import adam.composeapp.generated.resources.label_ADD
import adam.composeapp.generated.resources.label_CIL
import adam.composeapp.generated.resources.label_COLOR
import adam.composeapp.generated.resources.label_ClientInfo
import adam.composeapp.generated.resources.label_DP
import adam.composeapp.generated.resources.label_EJE
import adam.composeapp.generated.resources.label_ESF
import adam.composeapp.generated.resources.label_GlassesInfo
import adam.composeapp.generated.resources.label_LeftEye
import adam.composeapp.generated.resources.label_MountPrice
import adam.composeapp.generated.resources.label_RightEye
import adam.composeapp.generated.resources.label_SELL
import adam.composeapp.generated.resources.label_extraInfo
import adam.composeapp.generated.resources.label_extraProducts
import adam.composeapp.generated.resources.label_lensPrice
import adam.composeapp.generated.resources.label_totalLens
import adam.composeapp.generated.resources.menuGlassMethodOption_NoLens
import adam.composeapp.generated.resources.menuGlassMethodOption_bothGlassFromDB
import adam.composeapp.generated.resources.menuGlassMethodOption_bothGlassFromDB_priceInput
import adam.composeapp.generated.resources.tf_label_ClientID
import adam.composeapp.generated.resources.tf_label_ClientName
import adam.composeapp.generated.resources.tf_label_ClientNumber
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.ui.components.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.state.WindowStateHolder
import com.kerneloso.adam.util.longToPrice
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

class SellScreen : Screen {

    @Composable
    override fun Content() {
        WindowStateHolder.changeWindowCenteredSize( x = 1080.dp , y = 900.dp )
        viewTemplateWithNavigationBar {

            val mainScrollState = rememberScrollState()
            Column (
                Modifier
                    .fillMaxSize()
                    .verticalScroll(mainScrollState)
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
// ============================================================================================================================================
                // _   _            _       _     _
                //| | | |          (_)     | |   | |
                //| | | | __ _ _ __ _  __ _| |__ | | ___  ___
                //| | | |/ _` | '__| |/ _` | '_ \| |/ _ \/ __|
                //\ \_/ / (_| | |  | | (_| | |_) | |  __/\__ \
                // \___/ \__,_|_|  |_|\__,_|_.__/|_|\___||___/

                //Modifiers de contenedores
                    val mainContainerModifier = Modifier
                        .fillMaxWidth( 0.9f )
                        .background(color = MaterialTheme.colorScheme.background , shape = RoundedCornerShape(20.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)

                    val lensFormContainerModifier = Modifier
                        .fillMaxWidth(0.46f)
                        .background(color = MaterialTheme.colorScheme.background , shape = RoundedCornerShape(20.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)

                //Informacion dle cliente
                    var clientName by remember { mutableStateOf("") }
                    var clientNumber by remember { mutableStateOf("") }
                    var clientID by remember { mutableStateOf("") }

                //Informacion de los lentes
                    //Menu de metodo de seleccion de lentes
                        val glassMethodOptions = listOf(
                            stringResource(Res.string.menuGlassMethodOption_NoLens),
                            stringResource(Res.string.menuGlassMethodOption_bothGlassFromDB),
                            stringResource(Res.string.menuGlassMethodOption_bothGlassFromDB_priceInput),
                        )
                        var glassMethodMenuIsExpanded by remember { mutableStateOf(false) }
                        var glassMethodSelected by remember { mutableStateOf(glassMethodOptions[0]) }
                        var widthGlassMethodMenu by remember { mutableStateOf(0.dp) }

                    //Menu de seleccion de tipo de lente
                        // TODO : TRAER NOMBRES Y PRECIOS DESDE LA DB
                        val lensTypeOptions = listOf(
                            "Solo Consulta"
                        )
                        var lensTypeMenuIsExpanded by remember { mutableStateOf(false) }
                        var lensTypeSelected by remember { mutableStateOf("Solo Consulta") }
                        var widthLensTypeMenu by remember { mutableStateOf(0.dp) }

                    var leftLensPrice by remember {  mutableStateOf(0L) }
                    var rightLensPrice by remember {  mutableStateOf(0L) }

                    var priceTotalLens by remember {  mutableStateOf(0L) }

                    var leftESF by remember { mutableStateOf("") }
                    var leftCIL by remember { mutableStateOf("") }
                    var leftEJE by remember { mutableStateOf("") }
                    var leftADD by remember { mutableStateOf("") }

                    var rightESF by remember { mutableStateOf("") }
                    var rightCIL by remember { mutableStateOf("") }
                    var rightEJE by remember { mutableStateOf("") }
                    var rightADD by remember { mutableStateOf("") }

                //Datos Extras
                    var mountPrice by remember { mutableStateOf("") }
                    var dp by remember { mutableStateOf("") }
                    var color by remember { mutableStateOf("") }


                    var priceTotal by remember {  mutableStateOf(0L) }


                // TextField Values
                    val textFieldColors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,

                        focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,

                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,

                        cursorColor = MaterialTheme.colorScheme.onPrimary,

                        selectionColors = TextSelectionColors(
                            backgroundColor = MaterialTheme.colorScheme.secondary,
                            handleColor = MaterialTheme.colorScheme.secondary,
                        )
                    )

                    val textFieldModifiers = Modifier
                        .fillMaxWidth(0.46f)
                        .height(64.dp)

// ============================================================================================================================================

// ============================================================================================================================================
                // _____ _ _            _     _____       __
                ///  __ \ (_)          | |   |_   _|     / _|
                //| /  \/ |_  ___ _ __ | |_    | | _ __ | |_ ___
                //| |   | | |/ _ \ '_ \| __|   | || '_ \|  _/ _ \
                //| \__/\ | |  __/ | | | |_   _| || | | | || (_) |
                // \____/_|_|\___|_| |_|\__|  \___/_| |_|_| \___/
                ConstraintLayout(
                    modifier = mainContainerModifier
                ) {

                    val (
                        label,
                        textFieldClientName ,
                        textFieldClientNumber ,
                        textFieldClientID
                    ) = createRefs()

                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(Res.string.label_ClientInfo),
                        modifier = Modifier
                            .constrainAs(label) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    // CLIENT NAME
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.tf_label_ClientName),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = textFieldModifiers
                            .constrainAs(textFieldClientName) {
                                top.linkTo(label.bottom , margin = 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(textFieldClientNumber.start)
                            }
                    )

                    // CLIENT NUMBER
                    OutlinedTextField(
                        value = clientNumber,
                        onValueChange = { clientNumber = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.tf_label_ClientNumber),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = textFieldModifiers
                            .constrainAs(textFieldClientNumber) {
                                top.linkTo(label.bottom , margin = 16.dp)
                                start.linkTo(textFieldClientName.end)
                                end.linkTo(parent.end)
                            }
                    )

                    //CLIENT ID
                    OutlinedTextField(
                        value = clientID,
                        onValueChange = { clientID = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.tf_label_ClientID),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = textFieldModifiers
                            .constrainAs(textFieldClientID) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(textFieldClientName.bottom , margin = 20.dp)
                                bottom.linkTo(parent.bottom, margin = 16.dp)
                            }
                    )

                }
// ============================================================================================================================================
                Spacer(modifier = Modifier.height(20.dp))
// ============================================================================================================================================
                // _____ _                           _____       __
                //|  __ \ |                         |_   _|     / _|
                //| |  \/ | __ _ ___ ___  ___  ___    | | _ __ | |_ ___
                //| | __| |/ _` / __/ __|/ _ \/ __|   | || '_ \|  _/ _ \
                //| |_\ \ | (_| \__ \__ \  __/\__ \  _| || | | | || (_) |
                // \____/_|\__,_|___/___/\___||___/  \___/_| |_|_| \___/
                ConstraintLayout(
                    modifier = mainContainerModifier
                ) {

                    val (
                        label ,
                        menuSelectMethodLensInput ,
                    ) = createRefs()

                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(Res.string.label_GlassesInfo),
                        modifier = Modifier
                            .constrainAs(label) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    //MENU : SELECCIONAR METODO DE DEFINICION DE GAFAS
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(40.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .onGloballyPositioned { layoutCoordinates ->
                                widthGlassMethodMenu = layoutCoordinates.size.width.dp
                            }
                            .constrainAs(menuSelectMethodLensInput){
                                top.linkTo(label.bottom , margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)

                            }
                    ) {
                        TextButton(
                            onClick = { glassMethodMenuIsExpanded = true },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(glassMethodSelected)
                        }
                        DropdownMenu(
                            expanded = glassMethodMenuIsExpanded ,
                            onDismissRequest = { glassMethodMenuIsExpanded = false },
                            modifier = Modifier.width(widthGlassMethodMenu)
                        ) {
                            glassMethodOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        glassMethodSelected = option
                                        glassMethodMenuIsExpanded = false
                                    }
                                )
                            }
                        }
                    }


                    val (
                        leftEyeContainer ,
                        rightEyeContainer ,
                    ) = createRefs()
                    val marginTotalTop: ConstrainedLayoutReference
                    when ( glassMethodSelected ) {

                        // NO SE VENDERAN LENTES
                        stringResource(Res.string.menuGlassMethodOption_NoLens) -> {
                            marginTotalTop = menuSelectMethodLensInput
                            priceTotalLens = 0
                        } // case

                        // OBTENER PRECIO DE AMBOS LENTES DESDE LA DB
                        stringResource(Res.string.menuGlassMethodOption_bothGlassFromDB) -> {

                            //Left Eye
                            ConstraintLayout (
                                modifier = lensFormContainerModifier
                                    .constrainAs( leftEyeContainer ) {
                                        top.linkTo(menuSelectMethodLensInput.bottom, margin = 20.dp)
                                        start.linkTo(parent.start)
                                        end.linkTo(rightEyeContainer.start)
                                    }
                            ) {
                                val (
                                    titleLabel ,
                                    esf ,
                                    cil ,
                                    eje ,
                                    add ,
                                ) = createRefs()

                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = stringResource(Res.string.label_LeftEye),
                                    modifier = Modifier
                                        .constrainAs(titleLabel) {
                                            top.linkTo(parent.top , margin = 16.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )


                                // ESF
                                OutlinedTextField(
                                    value = leftESF,
                                    onValueChange = { leftESF = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ESF),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(esf) {
                                            top.linkTo(titleLabel.bottom , margin = 12.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(cil.start)
                                        }
                                )

                                //CIL
                                OutlinedTextField(
                                    value = leftCIL,
                                    onValueChange = { leftCIL = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_CIL),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(cil) {
                                            top.linkTo(titleLabel.bottom , margin = 12.dp)
                                            start.linkTo(esf.end)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // eje
                                OutlinedTextField(
                                    value = leftEJE,
                                    onValueChange = { leftEJE = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_EJE),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(eje) {
                                            top.linkTo(esf.bottom , margin = 12.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(add.start)
                                            bottom.linkTo(parent.bottom , margin = 12.dp)
                                        }
                                )

                                // add
                                OutlinedTextField(
                                    value = leftADD,
                                    onValueChange = { leftADD = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ADD),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(add) {
                                            top.linkTo(cil.bottom , margin = 12.dp)
                                            start.linkTo(eje.end)
                                            end.linkTo(parent.end)
                                            bottom.linkTo(parent.bottom , margin = 12.dp)
                                        }
                                )

                            } // Constraint Layout - Left Eye

                            //Right Eye
                            ConstraintLayout (
                                modifier = lensFormContainerModifier
                                    .constrainAs( rightEyeContainer ) {
                                        top.linkTo(menuSelectMethodLensInput.bottom, margin = 20.dp)
                                        start.linkTo(leftEyeContainer.end)
                                        end.linkTo(parent.end)
                                    }
                            ) {
                                val (
                                    titleLabel ,
                                    esf ,
                                    cil ,
                                    eje ,
                                    add ,
                                ) = createRefs()

                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = stringResource(Res.string.label_RightEye),
                                    modifier = Modifier
                                        .constrainAs(titleLabel) {
                                            top.linkTo(parent.top , margin = 16.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // ESF
                                OutlinedTextField(
                                    value = rightESF,
                                    onValueChange = { rightESF = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ESF),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(esf) {
                                            top.linkTo(titleLabel.bottom , margin = 12.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(cil.start)
                                        }
                                )

                                //CIL
                                OutlinedTextField(
                                    value = rightCIL,
                                    onValueChange = { rightCIL = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_CIL),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(cil) {
                                            top.linkTo(titleLabel.bottom , margin = 12.dp)
                                            start.linkTo(esf.end)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // EJE
                                OutlinedTextField(
                                    value = rightEJE,
                                    onValueChange = { rightEJE = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_EJE),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(eje) {
                                            top.linkTo(esf.bottom , margin = 12.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(add.start)
                                            bottom.linkTo(parent.bottom , margin = 12.dp)
                                        }
                                )

                                // ADD
                                OutlinedTextField(
                                    value = rightADD,
                                    onValueChange = { rightADD = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ADD),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(add) {
                                            top.linkTo(cil.bottom , margin = 12.dp)
                                            start.linkTo(eje.end)
                                            end.linkTo(parent.end)
                                            bottom.linkTo(parent.bottom , margin = 12.dp)
                                        }
                                )
                            } // Constraint Layout - Right Eye


                            // MENU DE SELECCION DE TIPO DE LENTE
                            val (
                                menuLensTypeSelector
                            ) = createRefs()
                            marginTotalTop = menuLensTypeSelector


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(40.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .onGloballyPositioned { layoutCoordinates ->
                                        widthGlassMethodMenu = layoutCoordinates.size.width.dp
                                    }
                                    .constrainAs(menuLensTypeSelector){
                                        top.linkTo(leftEyeContainer.bottom , margin = 12.dp)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)

                                    }
                            ) {
                                TextButton(
                                    onClick = { lensTypeMenuIsExpanded = true },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(lensTypeSelected)
                                }
                                DropdownMenu(
                                    expanded = lensTypeMenuIsExpanded ,
                                    onDismissRequest = { lensTypeMenuIsExpanded = false },
                                    modifier = Modifier.width(widthLensTypeMenu)
                                ) {
                                    glassMethodOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                lensTypeSelected = option
                                                lensTypeMenuIsExpanded = false
                                            }
                                        )
                                    }
                                }
                            }



                        } // Case

                        // OBTENER TIPO DE AMBOS LENTES DESDE LA DB , PRECIO MANUAL
                        stringResource(Res.string.menuGlassMethodOption_bothGlassFromDB_priceInput) -> {

                            //Left Eye
                            ConstraintLayout (
                                modifier = lensFormContainerModifier
                                    .constrainAs( leftEyeContainer ) {
                                        top.linkTo(menuSelectMethodLensInput.bottom, margin = 20.dp)
                                        start.linkTo(parent.start)
                                        end.linkTo(rightEyeContainer.start)
                                    }
                            ) {
                                val (
                                    titleLabel ,
                                    esf ,
                                    cil ,
                                    eje ,
                                    add ,
                                    priceInput
                                ) = createRefs()

                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = stringResource(Res.string.label_LeftEye),
                                    modifier = Modifier
                                        .constrainAs(titleLabel) {
                                            top.linkTo(parent.top , margin = 20.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // ESF
                                OutlinedTextField(
                                    value = leftESF,
                                    onValueChange = { leftESF = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ESF),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(esf) {
                                            top.linkTo(titleLabel.bottom , margin = 20.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(cil.start)
                                        }
                                )

                                //CIL
                                OutlinedTextField(
                                    value = leftCIL,
                                    onValueChange = { leftCIL = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_CIL),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(cil) {
                                            top.linkTo(titleLabel.bottom , margin = 20.dp)
                                            start.linkTo(esf.end)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // EJE
                                OutlinedTextField(
                                    value = leftEJE,
                                    onValueChange = { leftEJE = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_EJE),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(eje) {
                                            top.linkTo(esf.bottom , margin = 14.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(add.start)
                                        }
                                )

                                // ADD
                                OutlinedTextField(
                                    value = leftADD,
                                    onValueChange = { leftADD = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ADD),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(add) {
                                            top.linkTo(cil.bottom , margin = 14.dp)
                                            start.linkTo(eje.end)
                                            end.linkTo(parent.end)
                                        }
                                )


                                var textFieldValue by remember { mutableStateOf(TextFieldValue("0")) }
                                OutlinedTextField(
                                    value = textFieldValue,
                                    prefix = { Text("$") },
                                    onValueChange = { newValue ->
                                        val rawText = newValue.text.filter { it.isDigit() }

                                        if (rawText.matches(Regex("\\d{0,10}"))) {
                                            val cleaned = rawText.trimStart('0')

                                            val finalText = when {
                                                rawText.isEmpty() || cleaned.isEmpty() -> "0"
                                                else -> cleaned
                                            }

                                            textFieldValue = TextFieldValue(
                                                text = longToPrice(finalText.toLong()),
                                                selection = TextRange(longToPrice(finalText.toLong()).length)
                                            )
                                            leftLensPrice = finalText.toLongOrNull() ?: 0
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_lensPrice),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .height(60.dp)
                                        .constrainAs(priceInput) {
                                            top.linkTo(eje.bottom , margin = 20.dp)
                                            bottom.linkTo(parent.bottom , margin = 14.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )

                            } // Constraint Layout - Left Eye

                            //Right Eye
                            ConstraintLayout (
                                modifier = lensFormContainerModifier
                                    .constrainAs( rightEyeContainer ) {
                                        top.linkTo(menuSelectMethodLensInput.bottom, margin = 20.dp)
                                        start.linkTo(leftEyeContainer.end)
                                        end.linkTo(parent.end)
                                    }
                            ) {
                                val (
                                    titleLabel ,
                                    esf ,
                                    cil ,
                                    eje ,
                                    add ,
                                    priceInput
                                ) = createRefs()

                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    text = stringResource(Res.string.label_RightEye),
                                    modifier = Modifier
                                        .constrainAs(titleLabel) {
                                            top.linkTo(parent.top , margin = 20.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // ESF
                                OutlinedTextField(
                                    value = rightESF,
                                    onValueChange = { rightESF = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ESF),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(esf) {
                                            top.linkTo(titleLabel.bottom , margin = 20.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(cil.start)
                                        }
                                )

                                //CIL
                                OutlinedTextField(
                                    value = rightCIL,
                                    onValueChange = { rightCIL = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_CIL),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(cil) {
                                            top.linkTo(titleLabel.bottom , margin = 20.dp)
                                            start.linkTo(esf.end)
                                            end.linkTo(parent.end)
                                        }
                                )

                                // EJE
                                OutlinedTextField(
                                    value = rightEJE,
                                    onValueChange = { rightEJE = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_EJE),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(eje) {
                                            top.linkTo(esf.bottom , margin = 14.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(add.start)
                                        }
                                )

                                // ADD
                                OutlinedTextField(
                                    value = rightADD,
                                    onValueChange = { rightADD = it },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_ADD),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.36f)
                                        .height(60.dp)
                                        .constrainAs(add) {
                                            top.linkTo(esf.bottom , margin = 14.dp)
                                            start.linkTo(eje.end)
                                            end.linkTo(parent.end)
                                        }
                                )

                                var textFieldValue by remember { mutableStateOf(TextFieldValue("0")) }
                                OutlinedTextField(
                                    value = textFieldValue,
                                    prefix = { Text("$") },
                                    onValueChange = { newValue ->
                                        val rawText = newValue.text.filter { it.isDigit() }

                                        if (rawText.matches(Regex("\\d{0,10}"))) {
                                            val cleaned = rawText.trimStart('0')

                                            val finalText = when {
                                                rawText.isEmpty() || cleaned.isEmpty() -> "0"
                                                else -> cleaned
                                            }

                                            textFieldValue = TextFieldValue(
                                                text = longToPrice(finalText.toLong()),
                                                selection = TextRange(longToPrice(finalText.toLong()).length)
                                            )
                                            rightLensPrice = finalText.toLongOrNull() ?: 0
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(Res.string.label_lensPrice),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    },
                                    colors = textFieldColors,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .height(60.dp)
                                        .constrainAs(priceInput) {
                                            top.linkTo(eje.bottom , margin = 20.dp)
                                            bottom.linkTo(parent.bottom , margin = 14.dp)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                )


                            } // Constraint Layout - Right Eye

                            // Calcular precio total
                            priceTotalLens = leftLensPrice + rightLensPrice

                            val (
                                menuLensTypeSelector
                            ) = createRefs()
                            marginTotalTop = menuLensTypeSelector
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(40.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .onGloballyPositioned { layoutCoordinates ->
                                        widthGlassMethodMenu = layoutCoordinates.size.width.dp
                                    }
                                    .constrainAs(menuLensTypeSelector){
                                        top.linkTo(leftEyeContainer.bottom , margin = 20.dp)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)

                                    }
                            ) {
                                TextButton(
                                    onClick = { lensTypeMenuIsExpanded = true },
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(lensTypeSelected)
                                }
                                DropdownMenu(
                                    expanded = lensTypeMenuIsExpanded ,
                                    onDismissRequest = { lensTypeMenuIsExpanded = false },
                                    modifier = Modifier.width(widthLensTypeMenu)
                                ) {
                                    glassMethodOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                lensTypeSelected = option
                                                lensTypeMenuIsExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                        } // Case

                        else -> {
                            marginTotalTop = menuSelectMethodLensInput
                        }

                    } // when


                    val (
                        total
                    ) = createRefs()

                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "${stringResource(Res.string.label_totalLens)}${longToPrice(priceTotalLens)}",
                        modifier = Modifier
                            .constrainAs(total) {
                                top.linkTo(marginTotalTop.bottom , margin = 20.dp)
                                bottom.linkTo(parent.bottom , margin = 10.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                }
// ============================================================================================================================================
                Spacer(modifier = Modifier.height(20.dp))
// ============================================================================================================================================
                // _____     _              ______              _            _
                //|  ___|   | |             | ___ \            | |          | |
                //| |____  _| |_ _ __ __ _  | |_/ / __ ___   __| |_   _  ___| |_ ___
                //|  __\ \/ / __| '__/ _` | |  __/ '__/ _ \ / _` | | | |/ __| __/ __|
                //| |___>  <| |_| | | (_| | | |  | | | (_) | (_| | |_| | (__| |_\__ \
                //\____/_/\_\\__|_|  \__,_| \_|  |_|  \___/ \__,_|\__,_|\___|\__|___/
                ConstraintLayout(
                    modifier = mainContainerModifier
                ) {

                    val (
                        titleLabel ,
                        listContainer ,
                        addProdButton
                    ) = createRefs()


                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(Res.string.label_extraProducts),
                        modifier = Modifier
                            .constrainAs(titleLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )


                    //
                    ConstraintLayout (
                        modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            .fillMaxWidth()
                            .constrainAs(listContainer) {
                                top.linkTo(titleLabel.bottom , margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {

                        val (
                            header,
                            colum ,
                        ) = createRefs()

                        ConstraintLayout (
                            modifier = Modifier
                                .height(40.dp)
                                .constrainAs(header) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            val (
                                name,
                                quantity,
                                price,
                                del
                            ) = createRefs()


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .fillMaxHeight()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    .constrainAs(name) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(quantity.start)
                                    }
                            ) {
                                Text(
                                    text = "Nombre",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .fillMaxHeight()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    .constrainAs(quantity) {
                                        top.linkTo(parent.top)
                                        start.linkTo(name.end)
                                        end.linkTo(price.start)
                                    }
                            ) {
                                Text(
                                    text = "Cantidad",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .fillMaxHeight()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    .constrainAs(price) {
                                        top.linkTo(parent.top)
                                        start.linkTo(quantity.end)
                                        end.linkTo(del.start)
                                    }
                            ) {
                                Text(
                                    text = "Precio",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.095f)
                                    .fillMaxHeight()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                    .constrainAs(del) {
                                        top.linkTo(parent.top)
                                        start.linkTo(price.end)
                                        end.linkTo(parent.end)
                                    }
                            ) {
                                Text(
                                    text = "",
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }


                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .constrainAs(colum) {
                                    top.linkTo(header.bottom)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {  }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(60.dp)
                            .background(color = MaterialTheme.colorScheme.primary , shape = RoundedCornerShape(10.dp))
                            .constrainAs(addProdButton) {
                                top.linkTo(listContainer.bottom , margin = 20.dp)
                                bottom.linkTo(parent.bottom , margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {

                        Text(
                            style = MaterialTheme.typography.titleSmall,
                            fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = stringResource(Res.string.label_extraProducts),
                            modifier = Modifier
                                .align(Alignment.Center)

                        )

                    }

                }
// ============================================================================================================================================
                Spacer(modifier = Modifier.height(20.dp))
// ============================================================================================================================================
                // _____       __        _____     _
                //|_   _|     / _|      |  ___|   | |
                //  | | _ __ | |_ ___   | |____  _| |_ _ __ __ _
                //  | || '_ \|  _/ _ \  |  __\ \/ / __| '__/ _` |
                // _| || | | | || (_) | | |___>  <| |_| | | (_| |
                // \___/_| |_|_| \___/  \____/_/\_\\__|_|  \__,_|
                ConstraintLayout(
                    modifier = mainContainerModifier
                ) {

                    val (
                        titleLabel ,
                        textFieldMountPrice ,
                        textFieldColor ,
                        textFieldDP
                    ) = createRefs()

                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = stringResource(Res.string.label_extraInfo),
                        modifier = Modifier
                            .constrainAs(titleLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    // MOUNT PRICE
                    OutlinedTextField(
                        value = mountPrice,
                        onValueChange = { mountPrice = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.label_MountPrice),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.46f)
                            .height(60.dp)
                            .constrainAs(textFieldMountPrice) {
                                top.linkTo(titleLabel.bottom , margin = 12.dp)
                                start.linkTo(parent.start)
                                end.linkTo(textFieldColor.start)
                            }
                    )

                    // COLOR
                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.label_COLOR),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.46f)
                            .height(60.dp)
                            .constrainAs(textFieldColor) {
                                top.linkTo(titleLabel.bottom , margin = 12.dp)
                                start.linkTo(textFieldMountPrice.end)
                                end.linkTo(parent.end)
                            }
                    )

                    // DP
                    OutlinedTextField(
                        value = dp,
                        onValueChange = { dp = it },
                        label = {
                            Text(
                                text = stringResource(Res.string.label_DP),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.46f)
                            .height(60.dp)
                            .constrainAs(textFieldDP) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(textFieldMountPrice.bottom , margin = 20.dp)
                                bottom.linkTo(parent.bottom, margin = 14.dp)
                            }
                    )
                }
// ============================================================================================================================================
                Spacer(modifier = Modifier.height(20.dp))
// ============================================================================================================================================
                // _____      _ _  ______       _   _
                ///  ___|    | | | | ___ \     | | | |
                //\ `--.  ___| | | | |_/ /_   _| |_| |_ ___  _ __
                // `--. \/ _ \ | | | ___ \ | | | __| __/ _ \| '_ \
                ///\__/ /  __/ | | | |_/ / |_| | |_| || (_) | | | |
                //\____/ \___|_|_| \____/ \__,_|\__|\__\___/|_| |_|
                ConstraintLayout(
                    modifier = mainContainerModifier
                ) {

                    val (
                        titleLabel,
                        sellButton
                    ) = createRefs()


                    //Calcular Total
                    priceTotal = priceTotalLens

                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Total : $${longToPrice(priceTotal)}",
                        modifier = Modifier
                            .constrainAs(titleLabel) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(60.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .constrainAs(sellButton) {
                                top.linkTo(titleLabel.bottom, margin = 20.dp)
                                bottom.linkTo(parent.bottom, margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {

                        Text(
                            style = MaterialTheme.typography.titleSmall,
                            fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = stringResource(Res.string.label_SELL),
                            modifier = Modifier
                                .align(Alignment.Center)

                        )

                    }

                }
// ============================================================================================================================================
                Spacer(modifier = Modifier.height(20.dp))
// ============================================================================================================================================
            } //Column
        } //viewWhitNavigationBar
    } // Content ()
} // Screen () {}




