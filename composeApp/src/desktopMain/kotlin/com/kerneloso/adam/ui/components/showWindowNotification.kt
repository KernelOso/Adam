package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.Roboto_Bold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.constraintlayout.compose.ConstraintLayout
import com.kerneloso.adam.util.resizeAndCenterWindow
import org.jetbrains.compose.resources.Font

@Composable
fun showWindowNotification(
    title: String = "",
    text: String = "test",
    icon: ImageVector = Icons.Filled.Warning,
    color: Color = MaterialTheme.colorScheme.error,
    onClose: () -> Unit
) {

    Window(
        onCloseRequest = { onClose() },
        title = title,
        resizable = false,
        state = WindowState(),
        alwaysOnTop = true
    ) {
        val winWidth = 400
        val windHeight = 200
        LaunchedEffect(Unit){
            resizeAndCenterWindow(
                window = window,
                with = winWidth,
                height = windHeight
            )
        }

        viewWhitLogoBackground {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp , vertical = 20.dp)
            ) {
                val ( crIcon , crLabel ) = createRefs()

                Icon(
                    tint = color,
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .constrainAs(crIcon){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
                    color = color,
                    text = text,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .constrainAs(crLabel) {
                            top.linkTo(parent.top)
                            start.linkTo(crIcon.end , margin = 20.dp)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}