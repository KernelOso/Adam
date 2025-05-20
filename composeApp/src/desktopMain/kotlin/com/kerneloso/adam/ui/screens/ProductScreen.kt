package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.components.*
import com.kerneloso.adam.ui.viewmodel.ProductViewModel
import com.kerneloso.adam.ui.window.productFormWindow
import com.kerneloso.adam.util.*
import org.jetbrains.compose.resources.stringResource


class ProductScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        // Change size and position of the window
        val window = ComposeWindowHolder.window
        val winWidth = 1080
        val windHeight = 800
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        //SearchBar
        var searchText by remember { mutableStateOf("") }

        //View Model
        val viewModel = remember { (ProductViewModel()) }

        val productList = viewModel.productDB.value.products.filter {
            it.productName.contains( searchText , ignoreCase = true )
        }

        var isThisViewObfuscated by remember { mutableStateOf(false) }

        // Edit product
        var openEditProductForm by remember { mutableStateOf(false) }
        var productArg by remember { mutableStateOf(Product()) }

        // New Product
        var openNewProductForm by remember { mutableStateOf(false) }


        viewTemplateWithNavigationBar {

            val ( container ) = createRefs()

            primaryContainer(

                shapeRadius = 0.dp,
                modifier = Modifier
                    .padding(horizontal = 10.dp , vertical = 10.dp)
                    .fillMaxHeight(0.96f)
                    .fillMaxWidth()
                    .constrainAs(container){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                val ( crSearchBar , crList , crButton ) = createRefs()

                //SearchBar
                formTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = "Buscar Producto",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp)
                        .constrainAs(crSearchBar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(crList.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                //ListContainer
                secondaryContainer(
                    shapeRadius = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)
                        .constrainAs(crList){
                            top.linkTo(crSearchBar.bottom)
                            bottom.linkTo(crButton.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ){

                    val ( crHeaderContainer , crLazyColumn ) = createRefs()

                    tableHeaderRow(
                        modifier = Modifier.constrainAs(crHeaderContainer){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )


                    LazyColumn (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.925f)
                            .constrainAs(crLazyColumn) {
                                top.linkTo(crHeaderContainer.bottom)
                            }
                    ) {
                        items(productList) { product ->
                            tableItemRow(
                                product = product,
                                modifier = Modifier
                                    .clickable {
                                        productArg = product
                                        openEditProductForm = true
                                    }
                            )
                        }
                    }
                }

                simpleButton(
                    text = stringResource(Res.string.productScreen_button_newProduct),
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(60.dp)
                        .onClick {
                            openNewProductForm = true
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
        if (isThisViewObfuscated){
            obfuscateView()
        }

        //Open Edit product Window
        if (openEditProductForm){
            isThisViewObfuscated = true
            productFormWindow(
                onClose = { openEditProductForm = false ; isThisViewObfuscated = false} ,
                viewmodel = viewModel,
                product = productArg
            )
        }

        //Open New product Window
        if (openNewProductForm){
            isThisViewObfuscated = true
            productFormWindow(
                onClose = { openNewProductForm = false ; isThisViewObfuscated = false} ,
                viewmodel = viewModel
            )
        }
    }
}

@Composable
fun tableHeaderRow(
    modifier: Modifier
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()

    ) {
        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productScreen_tableHeader_id),
            modifier = Modifier
                .height(headerHeight)
                .weight(0.05f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productScreen_tableHeader_name),
            modifier = Modifier
                .height(headerHeight)
                .weight(0.45f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productScreen_tableHeader_type),
            modifier = Modifier
                .height(headerHeight)
                .weight(0.15f)
        )

        tableHeader(
            textStyle = headerTextStyle,
            text = stringResource(Res.string.productScreen_tableHeader_price),
            modifier = Modifier
                .height(headerHeight)
                .weight(0.35f)
        )
    }
}

@Composable
fun tableItemRow(
    modifier: Modifier = Modifier,
    product: Product
) {

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        val headerHeight = 40.dp
        val headerTextStyle = MaterialTheme.typography.titleSmall

        //Id
        tableItem(
            textStyle = headerTextStyle,
            text = product.productId.toString(),
            modifier = Modifier
                .height(headerHeight)
                .weight(0.05f)
        )

        //Name
        tableItem(
            textStyle = headerTextStyle,
            text = product.productName,
            modifier = Modifier
                .height(headerHeight)
                .weight(0.45f)
        )

        //Type
        tableItem(
            textStyle = headerTextStyle,
            text = product.productType,
            modifier = Modifier
                .height(headerHeight)
                .weight(0.15f)
        )

        //Price
        tableItem(
            textStyle = headerTextStyle,
            text = "$ ${longToPrice(product.productPrice)}",
            modifier = Modifier
                .height(headerHeight)
                .weight(0.35f)
        )
    }

}