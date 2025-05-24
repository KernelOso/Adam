package com.kerneloso.adam.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
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
import com.kerneloso.adam.domain.model.DatabaseItem
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.util.longToPrice
import java.time.LocalDate


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
            )
        },
        prefix = {
            if (prefix != null) {
                prefix()
            }
        },
//        colors = TextFieldDefaults.colors(
//
//            unfocusedContainerColor = MaterialTheme.colorScheme.background,
//            focusedContainerColor = MaterialTheme.colorScheme.background,
//
//            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
//            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
//
//            focusedSupportingTextColor = MaterialTheme.colorScheme.background,
//            unfocusedSupportingTextColor = MaterialTheme.colorScheme.background,
//
//            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
//            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
//
//            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
//
//            cursorColor = MaterialTheme.colorScheme.onPrimary,
//
//            selectionColors = TextSelectionColors(
//                backgroundColor = MaterialTheme.colorScheme.primary,
//                handleColor = MaterialTheme.colorScheme.primary,
//            )
//        ),
        singleLine = true,
        modifier = modifier
    )
}

@Composable
fun formCellNumberTextField(
    initialValue: Long = 0,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (Long) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(initialValue.toString())) }
    OutlinedTextField(
        value = textFieldValue,
        prefix = { Text("+ ") },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        onValueChange = { newText ->
            //filtar a solo numeros
            val rawText = newText.text.filter { it.isDigit() }

            //Limitar a 18 numeros
            if (rawText.matches(Regex("\\d{0,12}"))) {

                //Elimina los 0 a la izquierda
                val cleaned = rawText.trimStart('0')

                // devolver 0 si el string esta vacio
                val finalText = when {
                    rawText.isEmpty() || cleaned.isEmpty() -> "0"
                    else -> cleaned
                }

                //redefinir texto de salida
                textFieldValue = TextFieldValue(
                    text = finalText,
                    selection = TextRange(longToPrice(finalText.toLong()).length)
                )

                //Retornar el valor
                onValueChange( finalText.toLongOrNull() ?: 0 )
            }
        },
        modifier = modifier
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
        modifier = modifier
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : DatabaseItem> formDropdownMenu(
    prefix: String = "",
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    defaultOption: T,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }
    var optionSelected by remember { mutableStateOf(defaultOption) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredOptions = remember(searchQuery, options) {
        options.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

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
            Text(text = "${prefix}${optionSelected.name}")
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.width(dropdownWidth)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar...") },
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  formDropdownMenuGetButton(
    text: String,
    options: List<Product>,
    onOptionSelected: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredOptions = remember(searchQuery, options) {
        options.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary , shape = RoundedCornerShape(10.dp))
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
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.width(dropdownWidth)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar...") },
                singleLine = true,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        onOptionSelected(option)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}





@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  formDayMenu(
    initialValue: Int,
    options: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31),
    prefix: String = "",
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary , shape = RoundedCornerShape(10.dp))
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
            Text(
                text = "${prefix}${initialValue.toString()}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.width(dropdownWidth).fillMaxHeight(0.5f)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onOptionSelected(option)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  formMonthMenu(
    initialValue: Int,
    prefix: String = "",
    options: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10,11,12),
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary , shape = RoundedCornerShape(10.dp))
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
            Text(
                text = "${prefix}${initialValue.toString()}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier.width(dropdownWidth).fillMaxHeight(0.5f)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onOptionSelected(option)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun formYearMenu(
    initialValue: Int,
    prefix: String = "",
    futureYears: Int = 20,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentYear = LocalDate.now().year
    val options = (2000..(currentYear + futureYears)).toList()

    var isMenuExpanded by remember { mutableStateOf(false) }
    var dropdownWidth by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
            .onClick { isMenuExpanded = true }
            .onGloballyPositioned { coordinates ->
                dropdownWidth = coordinates.size.width.dp
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$prefix$initialValue",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier
                .width(dropdownWidth)
                .fillMaxHeight(0.5f)
        ) {
            options.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year.toString()) },
                    onClick = {
                        onOptionSelected(year)
                        isMenuExpanded = false
                    }
                )
            }
        }
    }
}


