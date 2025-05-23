package com.kerneloso.adam.ui.view.screen

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.component.homeButton
import com.kerneloso.adam.ui.component.viewTemplateWithNavigationBar
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class HomeScreen () : Screen {

    @Composable
    override fun Content() {

        val window = ComposeWindowHolder.window
        val winWidth = 700
        val windHeight = 700
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        viewTemplateWithNavigationBar {
            val ( container ) = createRefs()

            ConstraintLayout (
                modifier = Modifier
                    .width(600.dp)
                    .height(400.dp)
                    .constrainAs(container) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                val (
                    crButtonSell ,
                    crButtonSellers ,
                    crButtonRegisters ,
                    crButtonLens ,
                    crButtonFrames ,
                    crButtonProducts,
                ) = createRefs()

                //Button : Sell
                homeButton(
                    screenDestiny = SellScreen(),
                    icon = Icons.Filled.Store,
                    label = stringResource(Res.string.homeScreen_button_sell),
                    modifier = Modifier.constrainAs(crButtonSell) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                //Button : Sellers DB
                homeButton(
                    screenDestiny = HomeScreen(),
                    icon = Icons.Filled.SupervisorAccount,
                    label = stringResource(Res.string.homeScreen_button_sellers),
                    modifier = Modifier.constrainAs(crButtonSellers) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                //Button : Registers DB
                homeButton(
                    screenDestiny = HomeScreen(),
                    icon = Icons.Filled.FormatListNumbered,
                    label = stringResource(Res.string.homeScreen_button_registers),
                    modifier = Modifier.constrainAs(crButtonRegisters) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )


                //Button : Lens DB
                homeButton(
                    screenDestiny = LensScreen(),
                    icon = Icons.Filled.Camera,
                    label = stringResource(Res.string.homeScreen_button_lens),
                    modifier = Modifier.constrainAs(crButtonLens) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )

                //Button : Lens Frames DB DB
                homeButton(
                    screenDestiny = LensFramesScreen(),
                    icon = Icons.Filled.RemoveRedEye,
                    label = stringResource(Res.string.homeScreen_button_frames),
                    modifier = Modifier.constrainAs(crButtonFrames) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                //Button : Products DB
                homeButton(
                    screenDestiny = ProductsScreen(),
                    icon = Icons.Filled.ShoppingBag,
                    label = stringResource(Res.string.homeScreen_button_products),
                    modifier = Modifier.constrainAs(crButtonProducts) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}