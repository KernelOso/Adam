package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.view.window.productFormWindow
import com.kerneloso.adam.ui.viewmodel.LensViewModel
import com.kerneloso.adam.util.*
import org.jetbrains.compose.resources.stringResource

class LensScreen : Screen {

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

        //View Model
        val viewModel = remember { (LensViewModel()) }

        //Search Bar
        var searchText by remember { mutableStateOf("") }

        //List of lens
        var lensList: List<Lens> = viewModel.searchLens(searchText)

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditLensForm by remember { mutableStateOf(false) }
        var lensArg by remember { mutableStateOf(Lens()) }

        // New Product View
        var openNewLensForm by remember { mutableStateOf(false) }

        viewTemplateWithNavigationBar {

            val (container) = createRefs()

            container(
                shapeRadius = 4.dp,
                backgroundColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
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
                    searchNameValue = searchText,
                    onSearchNameValueChange = { searchText = it },
                    modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)
                        .constrainAs(crList) {
                            top.linkTo(crSearchBar.bottom)
                            bottom.linkTo(crButton.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {

                    val (crHeaderContainer, crLazyColumn) = createRefs()

                    tableHeaderRow(
                        modifier = Modifier.constrainAs(crHeaderContainer) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.925f)
                            .constrainAs(crLazyColumn) {
                                top.linkTo(crHeaderContainer.bottom)
                            }
                    ) {
                        items(lensList) { lens ->
                            tableItemRow(
                                lens = lens,
                                modifier = Modifier
                                    .clickable {
                                        lensArg = lens
                                        openEditLensForm = true
                                    }
                            )
                        }
                    }
                }

                simpleButton(
                    text = stringResource(Res.string.lensScreen_button_newLens),
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(60.dp)
                        .onClick {
                            openNewLensForm = true
                        }
                        .constrainAs(crButton) {
                            top.linkTo(crList.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }
        }

        if (isViewObfuscated) {
            obfuscateView()
        }

        //Open Edit Lens Window
        if (openEditLensForm) {
            isViewObfuscated = true
            productFormWindow(
                onClose = { openEditLensForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
                lens = lensArg
            )
        }

        //Open New Lens Window
        if (openNewLensForm) {
            isViewObfuscated = true
            productFormWindow(
                onClose = { openNewLensForm = false; isViewObfuscated = false },
                viewmodel = viewModel
            )
        }

    }
}

@Composable
fun searchBar(
    modifier: Modifier = Modifier,

    searchNameValue: String = "",
    onSearchNameValueChange : (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        formTextField(
            value = searchNameValue,
            onValueChange = { onSearchNameValueChange(it) },
            label = stringResource(Res.string.lensScreen_searchBar_byName),
            modifier = Modifier
                .weight(6f)
                .height(60.dp)
        )
    }
}

@Composable
fun tableHeaderRow(
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
            text = stringResource(Res.string.lensScreen_tableHeader_id),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.lensScreen_tableHeader_name),
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.lensScreen_tableHeader_price),
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }
}

@Composable
fun tableItemRow(
    modifier: Modifier = Modifier,
    lens: Lens
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
            text = lens.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Name
        tableItem(
            textStyle = headerTextStyle,
            text = lens.name,
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        //Price
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(lens.price)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }

}