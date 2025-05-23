package com.kerneloso.adam.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun tableHeader(
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
            )
    ) {
        Text(
            style = textStyle,
            text = text,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun tableItem(
    text: String,
    modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
    ) {
        Text(
            style = textStyle,
            text = text,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}