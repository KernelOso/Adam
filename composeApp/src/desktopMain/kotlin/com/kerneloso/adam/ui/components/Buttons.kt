package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.Roboto_Bold
import adam.composeapp.generated.resources.label_extraProducts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.kerneloso.adam.ui.screens.HomeScreen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun navigationButton (
    backgroundColor : Color = MaterialTheme.colorScheme.primary,
    shapeRadius: Dp = 10.dp,
    text: String,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth(0.3f)
            .height(60.dp)
            .background(color = backgroundColor , shape = RoundedCornerShape(shapeRadius))
    ) {

        Text(
            style = MaterialTheme.typography.titleSmall,
            fontFamily = FontFamily(Font(Res.font.Roboto_Bold)),
            color = MaterialTheme.colorScheme.onPrimary,
            text = text,
            modifier = Modifier
                .align(Alignment.Center)

        )

    }

}