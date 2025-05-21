package com.kerneloso.adam.domain.model

data class Lens(
    var id: Long = 0L,
    var name: String = "",
    var price: Long = 0L,
)

data class LensDB(
    var lastID: Long = 0L,
    var lens: List<Lens> = listOf(),
)
