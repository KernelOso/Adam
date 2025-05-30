package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.kerneloso.adam.domain.model.Seller
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.view.window.lensFormWindow
import com.kerneloso.adam.ui.view.window.sellersFormWindow
import com.kerneloso.adam.ui.viewmodel.SellersViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class SellersScreen : Screen {

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
        window.title = stringResource(Res.string.sellersScreen_windowTitle)

        //View Model
        val viewModel = remember { (SellersViewModel()) }

        //Search Bar
        var searchText by remember { mutableStateOf("") }

        //List of lens
        var sellerList: List<Seller> = viewModel.searchSellers(searchText)

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditSellerForm by remember { mutableStateOf(false) }
        var sellerArg by remember { mutableStateOf(Seller()) }

        // New Product View
        var openNewSellerForm by remember { mutableStateOf(false) }

        viewTemplateWithNavigationBar {

            val (container) = createRefs()

            container(
                shapeRadius = 6.dp,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                borderColor = MaterialTheme.colorScheme.primaryContainer,
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
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    backgroundColor = MaterialTheme.colorScheme.background,
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
                    Column {
                        tableHeaderRow(
                            modifier = Modifier
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(sellerList) { seller ->
                                tableItemRow(
                                    seller = seller,
                                    modifier = Modifier
                                        .clickable {
                                            sellerArg = seller
                                            openEditSellerForm = true
                                        }
                                )
                            }
                        }
                    }
                }

                simpleButton(
                    text = stringResource(Res.string.sellersScreen_button_newSeller),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(60.dp)
                        .onClick {
                            openNewSellerForm = true
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
        if (openEditSellerForm) {
            isViewObfuscated = true
            sellersFormWindow(
                onClose = { openEditSellerForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
                seller = sellerArg
            )
        }

        //Open New Lens Window
        if (openNewSellerForm) {
            isViewObfuscated = true
            sellersFormWindow(
                onClose = { openNewSellerForm = false; isViewObfuscated = false },
                viewmodel = viewModel
            )
        }
    }
}

@Composable
private fun searchBar(
    modifier: Modifier = Modifier,

    searchNameValue: String = "",
    onSearchNameValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        formTextField(
            value = searchNameValue,
            onValueChange = { onSearchNameValueChange(it) },
            label = stringResource(Res.string.sellersScreen_searchBar_byName),
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
            text = stringResource(Res.string.sellersScreen_tableHeader_id),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.sellersScreen_tableHeader_name),
            modifier = Modifier
                .height(headerHeight)
                .weight(9f)
        )
    }
}

@Composable
private fun tableItemRow(
    modifier: Modifier = Modifier,
    seller: Seller
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
            text = seller.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Name
        tableItem(
            textStyle = headerTextStyle,
            text = seller.name,
            modifier = Modifier
                .height(headerHeight)
                .weight(9f)
        )
    }
}
