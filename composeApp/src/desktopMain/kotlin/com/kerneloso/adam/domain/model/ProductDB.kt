package com.kerneloso.adam.domain.model

data class Product(
    var productId: Long,
    var productName: String,
    var productType: String,
    var productPrice: Long,
)


data class ProductDB(
    var lastProductId: Long = 0,
    var products: List<Product> = listOf(),
    var ProductsTypes: List<String> = listOf(
        "Lente",
        "Extra"
    )
)
