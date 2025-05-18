package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import org.jetbrains.compose.resources.painterResource

@Composable
fun viewWhitLogoBackground(
    content: @Composable ConstraintLayoutScope.() -> Unit
) {

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val ( logo ) = createRefs()

        // Logo background
        val colorMatrix = ColorMatrix().apply { setToSaturation(0f) }
        Image(
            alpha = 0.1f,
            painter = painterResource(Res.drawable.logo),
            contentDescription = null,
            colorFilter = ColorFilter.colorMatrix(colorMatrix),
            modifier = Modifier
                .fillMaxSize(0.8f)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        this.content()

    }

}