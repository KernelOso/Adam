package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.lensScreen_button_newLens
import adam.composeapp.generated.resources.lensScreen_tableHeader_id
import adam.composeapp.generated.resources.lensScreen_tableHeader_name
import adam.composeapp.generated.resources.lensScreen_tableHeader_price
import adam.composeapp.generated.resources.lensScreen_windowTitle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
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
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.domain.model.Register
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.container
import com.kerneloso.adam.ui.component.obfuscateView
import com.kerneloso.adam.ui.component.simpleButton
import com.kerneloso.adam.ui.component.tableHeader
import com.kerneloso.adam.ui.component.tableItem
import com.kerneloso.adam.ui.component.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.view.window.lensFormWindow
import com.kerneloso.adam.ui.viewmodel.RegistersViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class RegistersScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        // Change size and position of the window
        val window = ComposeWindowHolder.window
        val winWidth = 1080
        val windHeight = 800
        LaunchedEffect(Unit) {
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }
        window.title = stringResource(Res.string.lensScreen_windowTitle)

        //View Model
        val viewModel = remember { (RegistersViewModel()) }

        //Search Bar
        // luego  var searchText by remember { mutableStateOf("") }

        //List of lens
        val registerDB by viewModel.registerDB
        val registerList: List<Register> = registerDB.registers

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditRegisterForm by remember { mutableStateOf(false) }
        var registerArg by remember { mutableStateOf(Register()) }


        viewTemplateWithNavigationBar {

            val (container) = createRefs()

            container(
                shapeRadius = 6.dp,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                borderColor = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxSize(0.96f)
                    .constrainAs(container) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                val (crSearchBar, crList, crButton) = createRefs()

                //ListContainer
                container(
                    shapeRadius = 0.dp,
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                        .constrainAs(crList) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column {
                        tableHeaderRow(
                            modifier = Modifier
                        )
                        println(registerList)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(registerList) { register ->
                                tableItemRow(
                                    register = register,
                                    modifier = Modifier
                                        .clickable {
                                            registerArg = register
                                            openEditRegisterForm = true
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isViewObfuscated) {
            obfuscateView()
        }

        //Open Edit Lens Window
        if (openEditRegisterForm) {
            isViewObfuscated = true
//            lensFormWindow(
//                onClose = { openEditRegisterForm = false; isViewObfuscated = false },
//                viewmodel = viewModel,
//                lens = registerArg
//            )
        }
    }
}

@Composable
private fun tableHeaderRow(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        tableHeader(
            textStyle = headerTextStyle,
            text = "ID",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = "Date",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = "Client Name",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = "Client Id",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = "Total",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

    }
}

@Composable
private fun tableItemRow(
    modifier: Modifier = Modifier,
    register: Register
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        //Id
        tableItem(
            textStyle = headerTextStyle,
            text = register.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Date
        tableItem(
            textStyle = headerTextStyle,
            text = register.date,
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //client Name
        tableItem(
            textStyle = headerTextStyle,
            text = register.clientName,
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //client Id
        tableItem(
            textStyle = headerTextStyle,
            text = register.clientId.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //total :
        tableItem(
            textStyle = headerTextStyle,
            text = longToPrice(register.total),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )
    }
}