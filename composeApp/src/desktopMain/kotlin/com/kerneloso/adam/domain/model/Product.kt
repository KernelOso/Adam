package com.kerneloso.adam.domain.model

data class Product(
    var id: Long = 0L,
    var name: String = "",
    var price: Long = 0L,
)

data class ProductsDB(
    var lastID: Long = 0L,
    var products: List<Product> = listOf(),
)