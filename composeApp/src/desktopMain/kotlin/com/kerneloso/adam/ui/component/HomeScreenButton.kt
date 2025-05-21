package com.kerneloso.adam.ui.component

import adam.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.*
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun homeButton(
    screenDestiny : Screen,
    icon: ImageVector,
    label: String,
    modifier: Modifier
)  {
    val navigator = LocalNavigator.current

    Box(
        modifier = modifier
            .size(180.dp)
            .background(MaterialTheme.colorScheme.primary)
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