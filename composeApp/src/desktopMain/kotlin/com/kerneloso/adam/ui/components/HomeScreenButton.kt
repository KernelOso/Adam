package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.Roboto_Bold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.Font

private var navigator: Navigator? = null

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun homeButton(
    screenDestiny : Screen,
    icon: ImageVector,
    label: String,
    modifier: Modifier
) {


    //Navigator treatment
    if ( navigator == null ) {
        navigator = LocalNavigator.current
    }

    Box(
        modifier = modifier
            .onClick {
                navigator!!.push(screenDestiny)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val ( crIcon , crLabel ) = createRefs()

            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .constrainAs(crIcon){
                        top.linkTo(parent.top , margin = 20.dp)
                        bottom.linkTo(crLabel.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                color = MaterialTheme.colorScheme.onPrimary,
                text = label,
                modifier = Modifier
                    .constrainAs(crLabel) {
                        top.linkTo(crIcon.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}