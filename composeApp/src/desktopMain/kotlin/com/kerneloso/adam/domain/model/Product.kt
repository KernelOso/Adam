package com.kerneloso.adam.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Product(
    override var id: Long = 0L,
    override var name: String = "",
    var price: Long = 0L,
) : DatabaseItem

data class ProductsDB(
    var lastID: Long = 0L,
    var products: List<Product> = listOf(),
)

data class ProductTable(
    var product: Product,
    var quantity: Long = 1L
)