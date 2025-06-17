package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.lensScreen_windowTitle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.formWeekMenu
import com.kerneloso.adam.ui.component.obfuscateView
import com.kerneloso.adam.ui.component.tableHeader
import com.kerneloso.adam.ui.component.tableItem
import com.kerneloso.adam.ui.component.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.view.window.billFormWindow
import com.kerneloso.adam.ui.viewmodel.BillsViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.skia.Color
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class ResumeScreen : Screen {

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

        val viewModel = remember { (BillsViewModel()) }

        var selectedWeek by remember { mutableStateOf(getCurrentWeekCode()) }

        //List of lens
        val billList: List<Bill> = viewModel.searchBillsByWeek(selectedWeek)

        var totalGeneral = 0L
        var abonoGeneral = 0L
        if (billList.isNotEmpty()) {
            totalGeneral = billList.sumOf { it.total }
            abonoGeneral = billList.sumOf { it.abono }
        }

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditRegisterForm by remember { mutableStateOf(false) }
        var billArg by remember { mutableStateOf(Bill()) }


        viewTemplateWithNavigationBar {

            val (container) = createRefs()

            com.kerneloso.adam.ui.component.container(
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
                    selectedWeek = selectedWeek,
                    onSelectedWeek = { selectedWeek = it },

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
                com.kerneloso.adam.ui.component.container(
                    shapeRadius = 0.dp,
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.6f)
                        .constrainAs(crList) {
                            top.linkTo(crSearchBar.bottom)
                            bottom.linkTo(crButton.top)
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

                Row (
                    modifier = Modifier
                        //.background(MaterialTheme.colorScheme.primary)
                        .height(80.dp)
                        .fillMaxWidth(0.9f)
                        .constrainAs(crButton){
                        top.linkTo(crList.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {

                    Box(
                        modifier = Modifier.fillMaxHeight().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text("Abono Semanal: ", fontWeight = FontWeight.Bold , fontSize = 20.sp)
                            Text("$${longToPrice(abonoGeneral)}" , fontSize = 20.sp)
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxHeight().weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text("Total Semanal: ", fontWeight = FontWeight.Bold , fontSize = 20.sp)
                            Text("$${longToPrice(totalGeneral)}" , fontSize = 20.sp)
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

    selectedWeek: String,
    onSelectedWeek: (String) -> Unit,

    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        formWeekMenu(
            initialValue = selectedWeek,
            onWeekSelected = { onSelectedWeek(it) }
        )
    }
}

fun getCurrentWeekCode(): String {
    val today = LocalDate.now()
    val weekFields = WeekFields.of(Locale.getDefault())
    val weekNumber = today.get(weekFields.weekOfWeekBasedYear())
    val year = today.get(weekFields.weekBasedYear())

    return String.format("%04d-W%02d", year, weekNumber)
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

        //abono
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(bill.abono)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //total :
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(bill.total)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )
    }
}