package com.kerneloso.adam.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import cafe.adriel.voyager.navigator.LocalNavigator
import com.kerneloso.adam.io.FileUtil
import com.kerneloso.adam.ui.view.screen.HomeScreen
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun viewTemplateWithNavigationBar (
    content: @Composable ConstraintLayoutScope.() -> Unit
) {

    val navigator = LocalNavigator.current

    val color = MaterialTheme.colorScheme.primaryContainer
    val onColor = MaterialTheme.colorScheme.onPrimaryContainer

    Column {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color)
        ) {
            val ( crButtonBack , crButtonHome ,crTest  ) = createRefs()

            //Back
            Icon(
                tint = onColor,
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .onClick {
                        navigator?.pop()
                    }
                    .constrainAs(crButtonBack) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start , margin = 10.dp)
                    }
            )

            // HOME
            Icon(
                tint = onColor,
                imageVector = Icons.Filled.Home,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .onClick {
                        navigator?.push(HomeScreen())
                    }
                    .constrainAs(crButtonHome) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end , margin = 10.dp)
                    }
            )

            // SETTINGS
//            Icon(
//                tint = MaterialTheme.colorScheme.onPrimary,
//                imageVector = Icons.Filled.Settings,
//                contentDescription = "",
//                modifier = Modifier
//                    .size(34.dp)
//                    .constrainAs(buttonSettings) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                        start.linkTo(buttonHome.end , margin = 10.dp)
//                    }
//            )

        }

        viewWhitLogoBackground { this.content() }

    }

}