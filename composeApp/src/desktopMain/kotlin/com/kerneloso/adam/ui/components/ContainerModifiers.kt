package com.kerneloso.adam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun getMainContainerModifier ():Modifier {
    return Modifier
        .fillMaxWidth( 0.9f )
        .background(color = MaterialTheme.colorScheme.background , shape = RoundedCornerShape(20.dp))
        .border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(20.dp)
        )
        .padding(20.dp)
}
