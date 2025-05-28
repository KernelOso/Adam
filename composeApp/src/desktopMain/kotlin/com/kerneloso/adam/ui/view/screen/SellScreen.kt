package com.kerneloso.adam.ui.view.screen


import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.kerneloso.adam.domain.model.*
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.view.window.billCreated
import com.kerneloso.adam.ui.viewmodel.*
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter

class SellScreen : Screen { // Screen () {}

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        // Change size and position of the window
        val window = ComposeWindowHolder.window
        val winWidth = 1000
        val windHeight = 800
        LaunchedEffect(Unit) {
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }
        window.title = stringResource(Res.string.sellScreen_windowTitle)

        val navigator = LocalNavigator.current

        //viewModels
        val viewModel = remember { RegistersViewModel() }
        val sellersViewModel = remember { (SellersViewModel()) }
        val lensViewModel = remember { (LensViewModel()) }
        val frameViewModel = remember { (FramesViewModel()) }
        val productViewModel = remember { (ProductsViewModel()) }

        //dropdown menu default options
        val sellerNullOption = Seller(id = -1, name = stringResource(Res.string.sellScreen_formField_seller_nullOption))
        val lensNullOption = Lens(id = -1, name = "Lente no seleccionado")
        val frameNullOption = Frame(id = -1, name = "Montura no seleccionada")

        //scroll state
        val scrollState = rememberScrollState()

        //Variables
        //var register by remember { mutableStateOf(Register()) }

        val timeNow = LocalDateTime.now()

        var dateDay by remember { mutableStateOf(timeNow.dayOfMonth) }
        var dateMonth by remember { mutableStateOf(timeNow.monthValue) }
        var dateYear by remember { mutableStateOf(timeNow.year) }

        if ( dateMonth == 2 && dateDay > 28  ){
            if ( Year.isLeap(dateYear.toLong()) ){
                dateDay = 29
            } else {
                dateDay = 28
            }
        }

        var dateHour by remember { mutableStateOf(timeNow.hour) }
        var dateMinute by remember { mutableStateOf(timeNow.minute) }

        var formDate = LocalDateTime.of(dateYear, dateMonth, dateDay, dateHour, dateMinute)
        print(formDate)

        var formClientName by remember { mutableStateOf("") }
        var formClientNumber by remember { mutableStateOf(0L) }
        var formClientId by remember { mutableStateOf(0L) }

        var formSeller by remember { mutableStateOf(sellerNullOption) }

        var formFrame by remember { mutableStateOf(frameNullOption) }
        var formFramePrice by remember { mutableStateOf(0L) }
        LaunchedEffect(formFrame) {
            formFramePrice = formFrame.price
        }

        var formLens by remember { mutableStateOf(lensNullOption) }
        var formLensPrice by remember { mutableStateOf(0L) }
        LaunchedEffect(formLens) {
            formLensPrice = formLens.price
        }


        var odESF: String by remember { mutableStateOf("") }
        var odCIL: String by remember { mutableStateOf("") }
        var odEJE: String by remember { mutableStateOf("") }
        var odADD: String by remember { mutableStateOf("") }

        var oiESF: String by remember { mutableStateOf("") }
        var oiCIL: String by remember { mutableStateOf("") }
        var oiEJE: String by remember { mutableStateOf("") }
        var oiADD: String by remember { mutableStateOf("") }

        val formProducts = remember { mutableStateListOf<ProductTable>() }

        var formDP: String by remember { mutableStateOf("") }
        var formColor: String by remember { mutableStateOf("") }

        var formTotal by remember { mutableStateOf(0L) }

        //Separators
        val containerField = 580.dp
        val verticalSeparator = 30.dp
        val horizontalSeparator = 10.dp

        //Sizes
        var sellerNormalizedHeight by remember { mutableStateOf(0) }
        var flowRowWidth by remember { mutableStateOf(0) }

        //form verifications
        var errorEmptyClientName by remember { mutableStateOf(false) }
        var errorEmptySeller by remember { mutableStateOf(false) }
        var errorEmptyForm by remember { mutableStateOf(false) }

        var showBillCreatedWindow by remember { mutableStateOf(false) }
        var billArg by remember { mutableStateOf(Bill()) }

        var isViewObfuscated by remember { mutableStateOf(false) }

        //View
        viewTemplateWithNavigationBar {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                //______      _
                //|  _  \    | |
                //| | | |__ _| |_ ___
                //| | | / _` | __/ _ \
                //| |/ / (_| | ||  __/
                //|___/ \__,_|\__\___|
                container(
                    shapeRadius = 6.dp,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    borderColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .width(flowRowWidth.dp)
                        .height(100.dp),
                ) {
                    val (
                        row,
                    ) = createRefs()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(horizontalSeparator),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight()
                            .padding(vertical = 20.dp)
                            .constrainAs(row) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        formDayMenu(
                            initialValue = dateDay,
                            prefix = stringResource(Res.string.sellScreen_formField_date_dayPrefix),
                            month = dateMonth,
                            year = dateYear,
                            onOptionSelected = { dateDay = it },
                            modifier = Modifier
                                .weight(3f)
                                .height(80.dp)
                        )

                        formMonthMenu(
                            initialValue = dateMonth,
                            prefix = stringResource(Res.string.sellScreen_formField_date_monthPrefix),
                            onOptionSelected = { dateMonth = it },
                            modifier = Modifier
                                .weight(3f)
                                .height(80.dp)
                        )

                        formYearMenu(
                            initialValue = dateYear,
                            prefix = stringResource(Res.string.sellScreen_formField_date_yearPrefix),
                            onOptionSelected = { dateYear = it },
                            modifier = Modifier
                                .weight(3f)
                                .height(80.dp)
                        )

                    }

                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                // _____ _ _            _              _____      _ _
                ///  __ \ (_)          | |     ___    /  ___|    | | |
                //| /  \/ |_  ___ _ __ | |_   ( _ )   \ `--.  ___| | | ___ _ __
                //| |   | | |/ _ \ '_ \| __|  / _ \/\  `--. \/ _ \ | |/ _ \ '__|
                //| \__/\ | |  __/ | | | |_  | (_>  < /\__/ /  __/ | |  __/ |
                // \____/_|_|\___|_| |_|\__|  \___/\/ \____/ \___|_|_|\___|_|
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(horizontalSeparator),
                    verticalArrangement = Arrangement.spacedBy(verticalSeparator),
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        flowRowWidth = layoutCoordinates.size.width
                    }
                ) {

                    // _____ _ _            _     _____       __
                    ///  __ \ (_)          | |   |_   _|     / _|
                    //| /  \/ |_  ___ _ __ | |_    | | _ __ | |_ ___
                    //| |   | | |/ _ \ '_ \| __|   | || '_ \|  _/ _ \
                    //| \__/\ | |  __/ | | | |_   _| || | | | || (_) |
                    // \____/_|_|\___|_| |_|\__|  \___/_| |_|_| \___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField)
                            .onGloballyPositioned { layoutCoordinates ->
                                sellerNormalizedHeight = layoutCoordinates.size.height
                            },
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = formClientName,
                                onValueChange = { formClientName = it },
                                label = stringResource(Res.string.sellScreen_formField_clientName),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formCellNumberTextField(
                                initialValue = formClientNumber,
                                onValueChange = { formClientNumber = it },
                                label = stringResource(Res.string.sellScreen_formField_clientNumber),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formCellNumberTextField(
                                initialValue = formClientId,
                                onValueChange = { formClientId = it },
                                label = stringResource(Res.string.sellScreen_formField_clientNumberId),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }

                    // _____      _ _             _____       __
                    ///  ___|    | | |           |_   _|     / _|
                    //\ `--.  ___| | | ___ _ __    | | _ __ | |_ ___
                    // `--. \/ _ \ | |/ _ \ '__|   | || '_ \|  _/ _ \
                    ///\__/ /  __/ | |  __/ |     _| || | | | || (_) |
                    //\____/ \___|_|_|\___|_|     \___/_| |_|_| \___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .height(sellerNormalizedHeight.dp)
                            .width(containerField),
                    ) {

                        val (
                            label,
                            textField,
                        ) = createRefs()

                        Text(
                            text = stringResource(Res.string.sellScreen_formField_seller_title),
                            modifier = Modifier.constrainAs(label) {
                                bottom.linkTo(textField.top, margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        )

                        formDropdownMenu(
                            options = listOf(sellerNullOption) + sellersViewModel.sellersDB.value.sellers,
                            onOptionSelected = { formSeller = it },
                            prefix = stringResource(Res.string.sellScreen_formField_seller_prefix),
                            defaultOption = formSeller,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(80.dp)
                                .constrainAs(textField) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )


                    }
                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                // _                                __
                //| |                      ___     / _|
                //| |     ___ _ __  ___   ( _ )   | |_ _ __ __ _ _ __ ___   ___
                //| |    / _ \ '_ \/ __|  / _ \/\ |  _| '__/ _` | '_ ` _ \ / _ \
                //| |___|  __/ | | \__ \ | (_>  < | | | | | (_| | | | | | |  __/
                //\_____/\___|_| |_|___/  \___/\/ |_| |_|  \__,_|_| |_| |_|\___|
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(horizontalSeparator),
                    verticalArrangement = Arrangement.spacedBy(verticalSeparator)
                ) {
                    // _                      _____       __
                    //| |                    |_   _|     / _|
                    //| |     ___ _ __  ___    | | _ __ | |_ ___
                    //| |    / _ \ '_ \/ __|   | || '_ \|  _/ _ \
                    //| |___|  __/ | | \__ \  _| || | | | || (_) |
                    //\_____/\___|_| |_|___/  \___/_| |_|_| \___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Text("Informacion del Lente")
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formDropdownMenu(
                                options = listOf(lensNullOption , ) + lensViewModel.lensDB.value.lens,
                                onOptionSelected = { formLens = it },
                                prefix = "Lente : ",
                                defaultOption = formLens,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formPriceTextField(
                                initialValue =  formLensPrice,
                                label = "Precio del lente :",
                                onPriceChange = { formLensPrice = it },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }

                    //______                          _____       __
                    //|  ___|                        |_   _|     / _|
                    //| |_ _ __ __ _ _ __ ___   ___    | | _ __ | |_ ___
                    //|  _| '__/ _` | '_ ` _ \ / _ \   | || '_ \|  _/ _ \
                    //| | | | | (_| | | | | | |  __/  _| || | | | || (_) |
                    //\_| |_|  \__,_|_| |_| |_|\___|  \___/_| |_|_| \___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Text("Informacion de la montura")
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formDropdownMenu(
                                options = listOf(frameNullOption) + frameViewModel.framesDB.value.frames,
                                onOptionSelected = { formFrame = it },
                                prefix = "Montura : ",
                                defaultOption = formFrame,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formPriceTextField(
                                initialValue =  formFramePrice,
                                label = "Precio de la montura :",
                                onPriceChange = { formFramePrice = it },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }
                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                // _                       __                           _
                //| |                     / _|                         | |
                //| |     ___ _ __  ___  | |_ ___  _ __ _ __ ___  _   _| | __ _
                //| |    / _ \ '_ \/ __| |  _/ _ \| '__| '_ ` _ \| | | | |/ _` |
                //| |___|  __/ | | \__ \ | || (_) | |  | | | | | | |_| | | (_| |
                //\_____/\___|_| |_|___/ |_| \___/|_|  |_| |_| |_|\__,_|_|\__,_|
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(horizontalSeparator),
                    verticalArrangement = Arrangement.spacedBy(verticalSeparator)
                ) {
                    // _           __ _     _____             _____       __
                    //| |         / _| |   |  ___|           |_   _|     / _|
                    //| |     ___| |_| |_  | |__ _   _  ___    | | _ __ | |_ ___
                    //| |    / _ \  _| __| |  __| | | |/ _ \   | || '_ \|  _/ _ \
                    //| |___|  __/ | | |_  | |__| |_| |  __/  _| || | | | || (_) |
                    //\_____/\___|_|  \__| \____/\__, |\___|  \___/_| |_|_| \___/
                    //                            __/ |
                    //                           |___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Text(stringResource(Res.string.sellScreen_formTitle_OI))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = oiESF,
                                onValueChange = { oiESF = it },
                                label = stringResource(Res.string.sellScreen_formField_ESF),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = oiCIL,
                                onValueChange = { oiCIL = it },
                                label = stringResource(Res.string.sellScreen_formField_CIL),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = oiEJE,
                                onValueChange = { oiEJE = it },
                                label = stringResource(Res.string.sellScreen_formField_EJE),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = oiADD,
                                onValueChange = { oiADD = it },
                                label = stringResource(Res.string.sellScreen_formField_ADD),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }

                    //______ _       _     _     _____             _____       __
                    //| ___ (_)     | |   | |   |  ___|           |_   _|     / _|
                    //| |_/ /_  __ _| |__ | |_  | |__ _   _  ___    | | _ __ | |_ ___
                    //|    /| |/ _` | '_ \| __| |  __| | | |/ _ \   | || '_ \|  _/ _ \
                    //| |\ \| | (_| | | | | |_  | |__| |_| |  __/  _| || | | | || (_) |
                    //\_| \_|_|\__, |_| |_|\__| \____/\__, |\___|  \___/_| |_|_| \___/
                    //          __/ |                  __/ |
                    //         |___/                  |___/
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Text(stringResource(Res.string.sellScreen_formTitle_OD))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = odESF,
                                onValueChange = { odESF = it },
                                label = stringResource(Res.string.sellScreen_formField_ESF),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = odCIL,
                                onValueChange = { odCIL = it },
                                label = stringResource(Res.string.sellScreen_formField_CIL),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = odEJE,
                                onValueChange = { odEJE = it },
                                label = stringResource(Res.string.sellScreen_formField_EJE),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = odADD,
                                onValueChange = { odADD = it },
                                label = stringResource(Res.string.sellScreen_formField_ADD),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(20.dp))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }
                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                // _____     _              ______              _            _
                //|  ___|   | |             | ___ \            | |          | |
                //| |____  _| |_ _ __ __ _  | |_/ / __ ___   __| |_   _  ___| |_ ___
                //|  __\ \/ / __| '__/ _` | |  __/ '__/ _ \ / _` | | | |/ __| __/ __|
                //| |___>  <| |_| | | (_| | | |  | | | (_) | (_| | |_| | (__| |_\__ \
                //\____/_/\_\\__|_|  \__,_| \_|  |_|  \___/ \__,_|\__,_|\___|\__|___/
                container(
                    shapeRadius = 6.dp,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    borderColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .width(flowRowWidth.dp)
                        .height(600.dp),
                ) {
                    val (
                        table,
                        button,
                    ) = createRefs()

                    LazyColumn(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                            .fillMaxWidth(0.9f)
                            .height(400.dp)
                            .constrainAs(table) {
                                top.linkTo(parent.top, margin = verticalSeparator)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {

                        items(formProducts) { productRegist ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                val headerHeight = 40.dp
                                val headerTextStyle = MaterialTheme.typography.titleSmall

                                //Name
                                tableItem(
                                    textStyle = headerTextStyle,
                                    text = productRegist.product.name,
                                    modifier = Modifier
                                        .height(headerHeight)
                                        .weight(4f)
                                )


                                ConstraintLayout(
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.secondary,
                                        )
                                        .weight(2f)
                                        .height(headerHeight)
                                ) {

                                    val (
                                        decrease,
                                        quantity,
                                        increase
                                    ) = createRefs()

                                    Icon(
                                        tint = MaterialTheme.colorScheme.error,
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .constrainAs(decrease) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                start.linkTo(parent.start)
                                                end.linkTo(quantity.start)
                                            }
                                            .size(20.dp)
                                            .onClick {
                                                val existing = formProducts.find {
                                                    it.product.id == productRegist.product.id
                                                }

                                                if (existing != null) {
                                                    val index = formProducts.indexOfFirst { it.product.id == existing.product.id }
                                                    if (index != -1) {
                                                        val current = formProducts[index]
                                                        formProducts[index] = current.copy(quantity = current.quantity - 1)
                                                    }
                                                    existing.quantity -= 1
                                                    if (existing.quantity <= 0) {
                                                        formProducts.remove(existing)
                                                    }
                                                }
                                            }
                                    )

                                    Text(
                                        text = productRegist.quantity.toString(),
                                        modifier = Modifier.constrainAs(quantity) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(decrease.end)
                                            end.linkTo(increase.start)
                                        }
                                    )

                                    Icon(
                                        tint = MaterialTheme.colorScheme.primary,
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .constrainAs(increase) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                start.linkTo(quantity.end)
                                                end.linkTo(parent.end)
                                            }
                                            .size(20.dp)
                                            .onClick {
                                                val existing = formProducts.find {
                                                    it.product.id == productRegist.product.id
                                                }

                                                if (existing != null) {
                                                    val index = formProducts.indexOfFirst { it.product.id == existing.product.id }
                                                    if (index != -1) {
                                                        val current = formProducts[index]
                                                        formProducts[index] = current.copy(quantity = current.quantity + 1)
                                                    }
                                                    existing.quantity += 1
                                                }
                                            }
                                    )


                                }

                                //Price
                                tableItem(
                                    textStyle = headerTextStyle,
                                    text = "$ ${longToPrice(productRegist.product.price * productRegist.quantity)}",
                                    modifier = Modifier
                                        .height(headerHeight)
                                        .weight(3f)
                                )

                                ConstraintLayout(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(headerHeight)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.secondary,
                                        )
                                ) {
                                    val (
                                        icon
                                    ) = createRefs()

                                    Icon(
                                        tint = MaterialTheme.colorScheme.error,
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .constrainAs(icon) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                            }
                                            .onClick {
                                                val existing = formProducts.find {
                                                    it.product.id == productRegist.product.id
                                                }

                                                if (existing != null) {
                                                    formProducts.remove(existing)
                                                }
                                            }
                                    )

                                }
                            }
                        }
                    }

                    formDropdownMenuGetButton(
                        text = stringResource(Res.string.sellScreen_button_addProduct),
                        options = productViewModel.productsDB.value.products,
                        onOptionSelected = { productSelected ->
                            val existing = formProducts.find {
                                it.product.id == productSelected.id
                            }
                            if (existing != null) {
                                val index = formProducts.indexOfFirst { it.product.id == productSelected.id }
                                if (index != -1) {
                                    val current = formProducts[index]
                                    formProducts[index] = current.copy(quantity = current.quantity + 1)
                                }
                                existing.quantity += 1
                            } else {
                                formProducts += ProductTable(
                                    product = productSelected,
                                    quantity = 1L
                                )
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(0.5f)
                            .constrainAs(button) {
                                bottom.linkTo(parent.bottom, margin = verticalSeparator)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                //______                  _____       _
                //|  _  \         ___    /  __ \     | |
                //| | | |_ __    ( _ )   | /  \/ ___ | | ___  _ __
                //| | | | '_ \   / _ \/\ | |    / _ \| |/ _ \| '__|
                //| |/ /| |_) | | (_>  < | \__/\ (_) | | (_) | |
                //|___/ | .__/   \___/\/  \____/\___/|_|\___/|_|
                //      | |
                //      |_|
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(horizontalSeparator),
                    verticalArrangement = Arrangement.spacedBy(verticalSeparator)
                ) {
                    //Dp
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = formDP,
                                onValueChange = { formDP = it },
                                label = stringResource(Res.string.sellScreen_formField_DP),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }

                    //Color
                    container(
                        shapeRadius = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        borderColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .width(containerField),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            formTextField(
                                value = formColor,
                                onValueChange = { formColor = it },
                                label = stringResource(Res.string.sellScreen_formField_color),
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(80.dp)
                            )
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            Spacer(modifier = Modifier.height(verticalSeparator))
                            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        }
                    }
                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                // _____     _        _
                //|_   _|   | |      | |
                //  | | ___ | |_ __ _| |
                //  | |/ _ \| __/ _` | |
                //  | | (_) | || (_| | |
                //  \_/\___/ \__\__,_|_|

                formTotal =
                    (formLensPrice + formFramePrice + formProducts.sumOf { it.product.price * it.quantity })

                container(
                    shapeRadius = 6.dp,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    borderColor = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .width(flowRowWidth.dp),
                ) {

                    val (
                        label
                    ) = createRefs()

                    Text(
                        text = "Total : $${longToPrice(formTotal)}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(40.dp)
                            .constrainAs(label) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                }
                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
                simpleButton(
                    text = "Vender",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(80.dp)
                        .onClick {
                            verifyFormData (
                                sellerValue = formSeller,
                                showEmptySellerNotification = { errorEmptySeller = it },
                                selleDefaultValue = sellerNullOption.name,

                                clientNameValue = formClientName,
                                showEmptyClientNameNotification = { errorEmptyClientName = it },

                                totalValue = formTotal,
                                showEmptyFormNotification = { errorEmptyForm = it }
                            ) {
                                val newBill = Bill(
                                    id = viewModel.billDB.value.lastID + 1,
                                    date = formDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                                    clientName = formClientName,
                                    clientNumber = formClientNumber,
                                    clientId = formClientId,
                                    seller = formSeller,
                                    frame = formFrame,
                                    lens = formLens,
                                    odESF = odESF,
                                    odCIL = odCIL,
                                    odEJE = odEJE,
                                    odADD = odADD,
                                    oiESF = oiESF,
                                    oiCIL = oiCIL,
                                    oiEJE = oiEJE,
                                    oiADD = oiADD,
                                    products = formProducts,
                                    color = formColor,
                                    dp = formDP,
                                    total = formTotal
                                )

                                billArg = newBill
                                showBillCreatedWindow = true
                                print( "Hola!?" )

                                viewModel.addRegister(
                                    newBill
                                )
                            }
                        }
                )

                //=================================================================================
                Spacer(modifier = Modifier.height(verticalSeparator))
                //=================================================================================
            }




        }

        if (showBillCreatedWindow){
            isViewObfuscated = true
            billCreated(
                bill = billArg,
                onClose = { showBillCreatedWindow = false ; isViewObfuscated = false ; navigator !!. push (HomeScreen())  },
                viewmodel = viewModel
            )
        }


        if (errorEmptySeller){
            isViewObfuscated = true
            showWindowNotification(
                title = "Vendendor no ingresado",
                text = "No se selecciono un vendedor",
                onClose = { errorEmptySeller = false ; isViewObfuscated = false }
            )
        }

        if (errorEmptyClientName){
            isViewObfuscated = true
            showWindowNotification(
                title = "Nombre no ingresado",
                text = "No se ingreso el nombre del cliente",
                onClose = { errorEmptyClientName = false ; isViewObfuscated = false }
            )
        }

        if (errorEmptyForm){
            isViewObfuscated = true
            showWindowNotification(
                title = "No se vende nada",
                text = "No se esta vendiendo nada",
                onClose = { errorEmptyForm = false  ; isViewObfuscated = false}
            )
        }

        if (isViewObfuscated) {
            obfuscateView()
        }
    }
}


private fun verifyFormData (
    sellerValue: Seller = Seller(),
    selleDefaultValue: String,
    showEmptySellerNotification: (Boolean) -> Unit,

    clientNameValue: String = "",
    showEmptyClientNameNotification: (Boolean) -> Unit,

    totalValue: Long = 0L,
    showEmptyFormNotification: (Boolean) -> Unit,

    content: () -> Unit,
) {

    var clientNameIsEmpty = clientNameValue.isEmpty()
    var sellerIsEmpty = sellerValue.name.equals(selleDefaultValue)
    var totalIsEmpty = (totalValue == 0L)

    // Creamos una lista de referencias usando lambdas para poder modificarlas luego
    val boolRefs = listOf(
        Pair({ clientNameIsEmpty }, { value: Boolean -> clientNameIsEmpty = value }),
        Pair({ sellerIsEmpty }, { value: Boolean -> sellerIsEmpty = value }),
        Pair({ totalIsEmpty }, { value: Boolean -> totalIsEmpty = value })
    )

    // Paso 2: Filtrar cuáles están actualmente en `true`
    val trueRefs = boolRefs.filter { it.first() }

    // Paso 3: Si hay más de uno en true, dejar uno aleatorio en true y los demás en false
    if (trueRefs.size > 1) {
        val keepTrue = trueRefs.shuffled().first()
        for (ref in trueRefs) {
            ref.second(ref == keepTrue)
        }
    }
    showEmptySellerNotification(sellerIsEmpty)
    showEmptyClientNameNotification(clientNameIsEmpty)
    showEmptyFormNotification(totalIsEmpty)

    if ( !clientNameIsEmpty && !sellerIsEmpty && !totalIsEmpty ) {
        content()
    }
}



