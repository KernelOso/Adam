package com.kerneloso.adam.domain.model

data class Lens(
    override var id: Long = 0L,
    override var name: String = "",
    var price: Long = 0L,
) : DatabaseItem

data class LensDB(
    var lastID: Long = 0L,
    var lens: List<Lens> = listOf(),
)
