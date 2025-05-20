package com.kerneloso.adam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun primaryContainer (
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shapeRadius: Dp = 10.dp,
    content: @Composable ConstraintLayoutScope.() -> Unit
) {
    ConstraintLayout (
        modifier = modifier
            .clip(RoundedCornerShape(shapeRadius))
            .fillMaxWidth( 0.9f )
            .background(color = backgroundColor , shape = RoundedCornerShape(shapeRadius))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(shapeRadius)
            )
    ) {
        this.content()
    }
}

@Composable
fun secondaryContainer (
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shapeRadius: Dp = 10.dp,
    content: @Composable ConstraintLayoutScope.() -> Unit
) {
    ConstraintLayout (
        modifier = modifier
            .clip(RoundedCornerShape(shapeRadius))
            .fillMaxWidth( 0.9f )
            .background(color = backgroundColor , shape = RoundedCornerShape(shapeRadius))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(shapeRadius)
            )
    ) {
        this.content()
    }
}