package com.kerneloso.adam.domain.model

data class Seller(
    var sellerId : Long = 0L,
    var sellerName : String = ""
)

data class SellerDB(
    val lastSellerId : Long = 0L,
    var sellers : List<Seller> = listOf()
)