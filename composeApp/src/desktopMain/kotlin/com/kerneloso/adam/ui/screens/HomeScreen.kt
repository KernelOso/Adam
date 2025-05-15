package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.logo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.kerneloso.adam.ui.components.viewWithNavigationBar
import org.jetbrains.compose.resources.painterResource

class HomeScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {


        //Todo : pasar el navigator como argumento
        viewWithNavigationBar (

        ) {

            ConstraintLayout (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                val ( dummy , navBar , logo ,  buttonSell , buttonGlassDB , buttonSellersDB , buttonRegistersDB ) = createRefs()


                // Logo background
                val colorMatrix = ColorMatrix().apply { setToSaturation(0f) }
                Image(
                    alpha = 0.1f,
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(colorMatrix),
                    modifier = Modifier
                        .size(800.dp)
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )


                //Button : Sell
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(buttonSell) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(buttonGlassDB.start)
                        }
                ) {

                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val ( icon , label ) = createRefs()

                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Filled.Store,
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .constrainAs(icon){
                                    top.linkTo(parent.top , margin = 20.dp)
                                    bottom.linkTo(label.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        Text(
                            text = "Sell",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .constrainAs(label){
                                    top.linkTo(icon.bottom)
                                    bottom.linkTo(parent.bottom , margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                    }

                }

                //Button : Glass DB
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(buttonGlassDB) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(buttonSell.end)
                            end.linkTo(buttonSellersDB.start)
                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val ( icon , label ) = createRefs()

                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Filled.ShoppingBasket,
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .constrainAs(icon){
                                    top.linkTo(parent.top , margin = 20.dp)
                                    bottom.linkTo(label.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        Text(
                            text = "Glass DB",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .constrainAs(label){
                                    top.linkTo(icon.bottom)
                                    bottom.linkTo(parent.bottom , margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                    }
                }

                //Button : Sellers DB
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(buttonSellersDB) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(buttonGlassDB.end)
                            end.linkTo(buttonRegistersDB.end)

                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val ( icon , label ) = createRefs()

                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Filled.SupervisorAccount,
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .constrainAs(icon){
                                    top.linkTo(parent.top , margin = 20.dp)
                                    bottom.linkTo(label.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        Text(
                            text = "Sellers DB",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .constrainAs(label){
                                    top.linkTo(icon.bottom)
                                    bottom.linkTo(parent.bottom , margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                    }
                }

                //Button : Registers DB
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(buttonRegistersDB) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(buttonSellersDB.end)
                            end.linkTo(parent.end)

                        }
                ) {
                    ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val ( icon , label ) = createRefs()

                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.Filled.FormatListNumbered,
                            contentDescription = "",
                            modifier = Modifier
                                .size(80.dp)
                                .constrainAs(icon){
                                    top.linkTo(parent.top , margin = 20.dp)
                                    bottom.linkTo(label.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                        Text(
                            text = "Registers DB",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .constrainAs(label){
                                    top.linkTo(icon.bottom)
                                    bottom.linkTo(parent.bottom , margin = 20.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )

                    }
                }

            }

        }





    }
}