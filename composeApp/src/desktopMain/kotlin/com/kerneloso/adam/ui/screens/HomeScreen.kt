package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import com.kerneloso.adam.ui.ComposeWindowHolder
import com.kerneloso.adam.ui.components.homeButton
import com.kerneloso.adam.ui.components.viewTemplateWithNavigationBar
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.stringResource

class HomeScreen () : Screen {

    @Composable
    override fun Content() {

        val window = ComposeWindowHolder.window
        val winWidth = 600
        val windHeight = 600
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
                    .size(400.dp)
                    .constrainAs(container) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                val ( crButtonSell , crButtonProducts , crButtonSellers , crButtonRegisters ) = createRefs()



                //Button : Sell
                homeButton(
                    screenDestiny = SellScreen(),
                    icon = Icons.Filled.Store,
                    label = stringResource(Res.string.homeScreenButtonLabel_Sell),
                    modifier = Modifier.constrainAs(crButtonSell) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )



                //Button : Products DB
                homeButton(
                    screenDestiny = ProductScreen(),
                    icon = Icons.Filled.ShoppingBasket,
                    label = stringResource(Res.string.homeScreenButtonLabel_Products),
                    modifier = Modifier.constrainAs(crButtonProducts) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )



                //Button : Sellers DB
                homeButton(
                    screenDestiny = HomeScreen(),
                    icon = Icons.Filled.SupervisorAccount,
                    label = stringResource(Res.string.homeScreenButtonLabel_Sellers),
                    modifier = Modifier.constrainAs(crButtonSellers) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )



                //Button : Registers DB
                homeButton(
                    screenDestiny = HomeScreen(),
                    icon = Icons.Filled.FormatListNumbered,
                    label = stringResource(Res.string.homeScreenButtonLabel_Registers),
                    modifier = Modifier.constrainAs(crButtonRegisters) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}