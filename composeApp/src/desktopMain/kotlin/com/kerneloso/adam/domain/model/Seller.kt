package com.kerneloso.adam.domain.model

data class Seller(
    override var id: Long = 0L,
    override var name: String = "",
) : DatabaseItem

data class SellersDB(
    var lastID: Long = 0L,
    var sellers: List<Seller> = listOf(),
)