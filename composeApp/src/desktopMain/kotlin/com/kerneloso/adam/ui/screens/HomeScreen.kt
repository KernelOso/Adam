package com.kerneloso.adam.ui.screens

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.products_label
import adam.composeapp.generated.resources.registers_label
import adam.composeapp.generated.resources.sell_label
import adam.composeapp.generated.resources.vendors_label
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.kerneloso.adam.ui.components.homeButton
import com.kerneloso.adam.ui.components.viewTemplateWithNavigationBar
import com.kerneloso.adam.ui.state.WindowStateHolder
import org.jetbrains.compose.resources.stringResource

class HomeScreen : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        WindowStateHolder.changeWindowCenteredSize( x = 800.dp , y = 800.dp )

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
                    label = stringResource(Res.string.sell_label),
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(crButtonSell) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )



                //Button : Products DB
                homeButton(
                    screenDestiny = SellScreen(),
                    icon = Icons.Filled.ShoppingBasket,
                    label = stringResource(Res.string.products_label),
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(crButtonProducts) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )



                //Button : Sellers DB
                homeButton(
                    screenDestiny = SellScreen(),
                    icon = Icons.Filled.SupervisorAccount,
                    label = stringResource(Res.string.vendors_label),
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(crButtonSellers) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                )



                //Button : Registers DB
                homeButton(
                    screenDestiny = SellScreen(),
                    icon = Icons.Filled.FormatListNumbered,
                    label = stringResource(Res.string.registers_label),
                    modifier = Modifier
                        .size(180.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(crButtonRegisters) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}