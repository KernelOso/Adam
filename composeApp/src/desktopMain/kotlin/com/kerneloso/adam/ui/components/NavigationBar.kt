package com.kerneloso.adam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun viewWithNavigationBar (
    content: @Composable () -> Unit
) {

    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            ConstraintLayout (
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val ( dummy , buttonSettings , buttonHome ) = createRefs()



                // BUTTON : SETTINGS
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .constrainAs(buttonSettings) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end , margin = 20.dp)
                        }
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                    )

                }



                // BUTTON : HOME
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .constrainAs(buttonHome) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start , margin = 20.dp)
                        }
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        imageVector = Icons.Filled.Home,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                    )

                }



            }
        }

        content.invoke()

    }

}