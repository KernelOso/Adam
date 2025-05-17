package com.kerneloso.adam.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun longToPrice(value: Long): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val format = DecimalFormat("#,###", symbols)
    return format.format(value)
}


fun priceToLong(value: String): Long {
    val cleaned = value.filter { it.isDigit() || it == '$' || it == '.' }
    val numeric = cleaned.replace("$", "").replace(".", "")
    return numeric.toLong()
}