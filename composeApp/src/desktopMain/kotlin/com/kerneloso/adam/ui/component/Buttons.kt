package com.kerneloso.adam.ui.component

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.Roboto_Bold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font


@Composable
fun simpleButton (
    backgroundColor : Color = MaterialTheme.colorScheme.primary,
    shapeRadius: Dp = 10.dp,
    text: String,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .background(color = backgroundColor , shape = RoundedCornerShape(shapeRadius))
    ) {

        Text(
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
            color = MaterialTheme.colorScheme.onPrimary,
            text = text,
            modifier = Modifier
                .align(Alignment.Center)

        )
    }
}