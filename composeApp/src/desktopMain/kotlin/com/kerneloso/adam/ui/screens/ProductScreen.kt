package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.label_extraProducts
import adam.composeapp.generated.resources.productScreen_tableHeader_id
import adam.composeapp.generated.resources.productScreen_tableHeader_name
import adam.composeapp.generated.resources.productScreen_tableHeader_price
import adam.composeapp.generated.resources.productScreen_tableHeader_type
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.ui.components.navigationButton
import com.kerneloso.adam.ui.components.primaryContainer
import com.kerneloso.adam.ui.components.secondaryContainer
import com.kerneloso.adam.ui.components.tableHeader
import com.kerneloso.adam.ui.components.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.state.WindowStateHolder
import org.jetbrains.compose.resources.stringResource


class ProductScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        LaunchedEffect(Unit){
            WindowStateHolder.changeWindowCenteredSize( x = 900.dp , y = 800.dp )
        }

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
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ){

                    val ( crHeaderContainer , crLazyColumn ) = createRefs()

                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(crHeaderContainer){
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
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

                    //TODO : lazy Column



                }

                    navigationButton(
                        text = stringResource(Res.string.label_extraProducts),
                        modifier = Modifier
                            .onClick {
                                //OPEN NEW WINDOW
                            }
                            .constrainAs(button){
                            top.linkTo(list.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
            }

        }

    }
}