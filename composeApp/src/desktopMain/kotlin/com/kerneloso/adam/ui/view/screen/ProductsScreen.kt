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
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.*
import com.kerneloso.adam.ui.view.window.productFormWindow
import com.kerneloso.adam.ui.viewmodel.ProductsViewModel
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class ProductsScreen : Screen {

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
        window.title = stringResource(Res.string.productsScreen_windowTitle)

        //View Model
        val viewModel = remember { (ProductsViewModel()) }

        //Search Bar
        var searchText by remember { mutableStateOf("") }

        //List of lens
        var productsList: List<Product> = viewModel.searchProducts(searchText)

        //View Obfuscated State
        var isViewObfuscated by remember { mutableStateOf(false) }

        //Views triggers :
        // Edit product View
        var openEditProductsForm by remember { mutableStateOf(false) }
        var productArg by remember { mutableStateOf(Product()) }

        // New Product View
        var openNewProductsForm by remember { mutableStateOf(false) }

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
                            items(productsList) { product ->
                                tableItemRow(
                                    product = product,
                                    modifier = Modifier
                                        .clickable {
                                            productArg = product
                                            openEditProductsForm = true
                                        }
                                )
                            }
                        }
                    }
                }

                simpleButton(
                    text = stringResource(Res.string.productsScreen_button_newProduct),
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(60.dp)
                        .onClick {
                            openNewProductsForm = true
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
        if (openEditProductsForm) {
            isViewObfuscated = true
            productFormWindow(
                onClose = { openEditProductsForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
                product = productArg
            )
        }

        //Open New Lens Window
        if (openNewProductsForm) {
            isViewObfuscated = true
            productFormWindow(
                onClose = { openNewProductsForm = false; isViewObfuscated = false },
                viewmodel = viewModel,
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
            label = stringResource(Res.string.productsScreen_searchBar_byName),
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
            text = stringResource(Res.string.productsScreen_tableHeader_id),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productsScreen_tableHeader_name),
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productsScreen_tableHeader_price),
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }
}

@Composable
private fun tableItemRow(
    modifier: Modifier = Modifier,
    product: Product
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
            text = product.id.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(1f)
        )

        //Name
        tableItem(
            textStyle = headerTextStyle,
            text = product.name,
            modifier = Modifier
                .height(headerHeight)
                .weight(5f)
        )

        //Price
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(product.price)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(4f)
        )
    }
}