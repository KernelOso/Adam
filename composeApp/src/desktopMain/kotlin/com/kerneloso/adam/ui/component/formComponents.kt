package com.kerneloso.adam.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kerneloso.adam.util.longToPrice

@Composable
fun formTextField(
    value: String,
    label: String = "",
    prefix: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = placeholder,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        prefix = {
            if (prefix != null) {
                prefix()
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,

            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,

            focusedTextColor = MaterialTheme.colorScheme.onPrimary,

            cursorColor = MaterialTheme.colorScheme.onPrimary,

            selectionColors = TextSelectionColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                handleColor = MaterialTheme.colorScheme.primary,
            )
        ),
        singleLine = true,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun formPriceTextField(
    initialValue: Long = 0,
    label: String,
    modifier: Modifier = Modifier,
    onPriceChange: (Long) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(longToPrice(initialValue))) }
    OutlinedTextField(
        value = textFieldValue,
        prefix = { Text("$ ") },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        },
        onValueChange = { newText ->
            val rawText = newText.text.filter { it.isDigit() }

            //Limitar a 18 numeros
            if (rawText.matches(Regex("\\d{0,18}"))) {

                //Elimina los 0 a la izquierda
                val cleaned = rawText.trimStart('0')

                // devolver 0 si el string esta vacio
                val finalText = when {
                    rawText.isEmpty() || cleaned.isEmpty() -> "0"
                    else -> cleaned
                }

                // formatear en formato de precio
                val outputTF = longToPrice(finalText.toLong())
                textFieldValue = TextFieldValue(
                    text = outputTF,
                    selection = TextRange(longToPrice(finalText.toLong()).length)
                )

                //Retornar el valor
                onPriceChange( finalText.toLongOrNull() ?: 0 )
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,

            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,

            focusedTextColor = MaterialTheme.colorScheme.onPrimary,

            cursorColor = MaterialTheme.colorScheme.onPrimary,

            selectionColors = TextSelectionColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                handleColor = MaterialTheme.colorScheme.primary,
            )
        ),
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun formDropdownMenu(
    prefix: String = "",
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    defaultOption: String,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }
    var optionSelected by remember { mutableStateOf(defaultOption) }
    onOptionSelected(defaultOption)
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(4.dp)
            )
            .onClick {
                isMenuExpanded = true
            }
            .onGloballyPositioned { layoutCoordinates ->
                dropdownWidth = layoutCoordinates.size.width.dp
            }
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${prefix}${optionSelected}")
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.width(dropdownWidth)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        optionSelected = option
                        onOptionSelected(option)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}