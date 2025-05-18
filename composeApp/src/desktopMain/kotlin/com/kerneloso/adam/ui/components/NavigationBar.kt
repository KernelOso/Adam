package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.logo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import cafe.adriel.voyager.navigator.LocalNavigator
import com.kerneloso.adam.ui.screens.HomeScreen
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun viewTemplateWithNavigationBar (
    content: @Composable ConstraintLayoutScope.() -> Unit
) {

    val navigator = LocalNavigator.current

    Column {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val ( buttonSettings , buttonHome  ) = createRefs()

            // HOME
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Filled.Home,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .onClick {
                        navigator?.push(HomeScreen())
                    }
                    .constrainAs(buttonHome) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start , margin = 10.dp)
                    }
            )

            // SETTINGS
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Filled.Settings,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .constrainAs(buttonSettings) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(buttonHome.end , margin = 10.dp)
                    }
            )

        }

        viewWhitLogoBackground { this.content() }

    }

}