package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.label_extraProducts
import adam.composeapp.generated.resources.productScreen_tableHeader_id
import adam.composeapp.generated.resources.productScreen_tableHeader_name
import adam.composeapp.generated.resources.productScreen_tableHeader_price
import adam.composeapp.generated.resources.productScreen_tableHeader_type
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.components.simpleButton
import com.kerneloso.adam.ui.components.obfuscateView
import com.kerneloso.adam.ui.components.primaryContainer
import com.kerneloso.adam.ui.components.secondaryContainer
import com.kerneloso.adam.ui.components.tableHeader
import com.kerneloso.adam.ui.components.tableItem
import com.kerneloso.adam.ui.components.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.viewmodel.ProductViewModel
import com.kerneloso.adam.ui.window.productFormWindow
import com.kerneloso.adam.util.longToPrice
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource


class ProductScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
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



        val viewModel = remember { (ProductViewModel()) }



        var isThisViewObfuscated by remember { mutableStateOf(false) }

        var openEditProductForm by remember { mutableStateOf(false) }
        var productArg by remember { mutableStateOf(Product()) }

        var openNewProductForm by remember { mutableStateOf(false) }


        viewTemplateWithNavigationBar {

            val ( container ) = createRefs()

            primaryContainer(
                padding = 10.dp,
                shapeRadius = 0.dp,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .constrainAs(container){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                val ( list , button ) = createRefs()

                //ListContainer
                secondaryContainer(
                    shapeRadius = 0.dp,
                    padding = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .constrainAs(list){
                            top.linkTo(parent.top)
                            bottom.linkTo(button.top)
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
                            .fillMaxHeight()
                            .constrainAs(crLazyColumn) {
                                top.linkTo(crHeaderContainer.bottom)
                            }
                    ) {
                        items(viewModel.productDB.value.products) { product ->
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
                    text = stringResource(Res.string.label_extraProducts),
                    modifier = Modifier
                        .onClick {
                            openNewProductForm = true
                        }
                        .constrainAs(button) {
                            top.linkTo(list.bottom)
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



        //Edit product
        if (openEditProductForm){
            isThisViewObfuscated = true
            productFormWindow(
                onClose = { openEditProductForm = false ; isThisViewObfuscated = false} ,
                viewmodel = viewModel,
                product = productArg
            )
        }

        //New Product
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
    //Header
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