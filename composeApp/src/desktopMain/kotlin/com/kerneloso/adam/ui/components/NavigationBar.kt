package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.app_name
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kerneloso.adam.ui.state.WindowStateHolder
import org.jetbrains.compose.resources.stringResource
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun viewWithNavigationBar (
    content: @Composable () -> Unit
) {

    Column {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val ( dummy , buttonSettings , buttonHome  ) = createRefs()

            // HOME
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Filled.Home,
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
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

        content.invoke()

    }

}