package com.kerneloso.adam.domain.model

data class Frame(
    var id: Long = 0L,
    var name: String = "",
    var price: Long = 0L,
)

data class FramesDB(
    var lastID: Long = 0L,
    var frames: List<Frame> = listOf(),
)
