package com.kerneloso.adam.domain.model

data class Frame(
    override var id: Long = 0L,
    override var name: String = "",
    var price: Long = 0L,
) : DatabaseItem

data class FramesDB(
    var lastID: Long = 0L,
    var frames: List<Frame> = listOf(),
)
