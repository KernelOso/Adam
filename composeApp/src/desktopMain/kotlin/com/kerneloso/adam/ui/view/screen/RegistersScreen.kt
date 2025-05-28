package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.lensScreen_windowTitle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.container
import com.kerneloso.adam.ui.component.formTextField
import com.kerneloso.adam.ui.component.obfuscateView
import com.kerneloso.adam.ui.component.tableHeader
import com.kerneloso.adam.ui.component.tableItem
import com.kerneloso.adam.ui.component.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.view.window.billFormWindow
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
        var searchDate by remember { mutableStateOf("") }
        var searchClientName by remember { mutableStateOf("") }
        var searchBillId by remember { mutableStateOf("") }

        //List of lens
        val registerDB by viewModel.billDB
        val billList: List<Bill> = viewModel.searchRegisters(searchBillId , searchDate , searchClientName)

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditRegisterForm by remember { mutableStateOf(false) }
        var billArg by remember { mutableStateOf(Bill()) }


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

                searchBar(
                    searchIdValue = searchBillId,
                    onSearchIdValue = { searchBillId = it },

                    searchDateValue = searchDate,
                    onSearchDateValue = { searchDate = it },

                    searchClientNameValue = searchClientName,
                    onSearchClientNameValue = { searchClientName = it },


                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(80.dp)
                        .constrainAs(crSearchBar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(crList.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )

                //ListContainer
                container(
                    shapeRadius = 0.dp,
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                        .constrainAs(crList) {
                            top.linkTo(crSearchBar.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column {
                        tableHeaderRow(
                            modifier = Modifier
                        )
                        println(billList)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(billList) { bill ->
                                tableItemRow(
                                    bill = bill,
                                    modifier = Modifier
                                        .clickable {
                                            billArg = bill
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
            billFormWindow(
                onClose = { openEditRegisterForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
                bill = billArg
            )
        }
    }
}

@Composable
private fun searchBar(
    modifier: Modifier = Modifier,

    searchIdValue: String = "",
    onSearchIdValue: (String) -> Unit,


    searchDateValue: String = "",
    onSearchDateValue: (String) -> Unit,

    searchClientNameValue: String = "",
    onSearchClientNameValue: (String) -> Unit,

    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        formTextField(
            value = searchIdValue,
            onValueChange = { onSearchIdValue(it) },
            label = "ID :",
            modifier = Modifier
                .weight(2f)
                .height(60.dp)
        )

        Spacer(modifier=Modifier.width(20.dp))

        formTextField(
            value = searchDateValue,
            onValueChange = { onSearchDateValue(it) },
            label = "Fecha :",
            placeholder = {
                Text(
                    text = "DD-MM-YYYY",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            modifier = Modifier
                .weight(2f)
                .height(60.dp)
        )

        Spacer(modifier=Modifier.width(20.dp))

        formTextField(
            value = searchClientNameValue,
            onValueChange = { onSearchClientNameValue(it) },
            label = "Nombre del cliente :",
            modifier = Modifier
                .weight(6f)
                .height(60.dp)
        )
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
            text = "Abono",
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
    bill: Bill
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
            text = bill.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Date
        tableItem(
            textStyle = headerTextStyle,
            text = bill.date,
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //client Name
        tableItem(
            textStyle = headerTextStyle,
            text = bill.clientName,
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //client Id
        tableItem(
            textStyle = headerTextStyle,
            text = bill.abono.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //total :
        tableItem(
            textStyle = headerTextStyle,
            text = longToPrice(bill.total),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )
    }
}