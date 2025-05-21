package com.kerneloso.adam.domain.model

data class Product(
    var productId: Long = 0L,
    var productName: String = "",
    var productType: String = "",
    var productPrice: Long = 0L,
)


data class ProductDB(
    var lastProductId: Long = 0L,
    var products: List<Product> = listOf(),
    var ProductsTypes: List<String> = listOf(
        "Lente",
        "Extra",
        "Montura"
    )
)
