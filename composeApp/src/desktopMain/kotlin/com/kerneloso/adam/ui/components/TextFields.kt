package com.kerneloso.adam.ui.components

import adam.composeapp.generated.resources.Res
import adam.composeapp.generated.resources.productFormWindow_form_productName
import adam.composeapp.generated.resources.productFormWindow_form_productPrice
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kerneloso.adam.util.longToPrice
import org.jetbrains.compose.resources.stringResource

@Composable
fun formTextField(
    value: String,
    label: String,
    prefix: @Composable() (() -> Unit)? = null,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
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
    var textFieldValue by remember { mutableStateOf(TextFieldValue("0")) }
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


