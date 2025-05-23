package com.kerneloso.adam.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
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
fun container (
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    borderWidth: Dp = 2.dp,
    shapeRadius: Dp = 10.dp,
    content: @Composable ConstraintLayoutScope.() -> Unit
) {
    ConstraintLayout (
        modifier = modifier
            .background(color = backgroundColor , shape = RoundedCornerShape(shapeRadius))
            .clip(RoundedCornerShape(shapeRadius))
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(shapeRadius)
            )
    ) {
        this.content()
    }
}