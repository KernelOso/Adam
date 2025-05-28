package com.kerneloso.adam.domain.model

data class Bill(
    var id: Long = 0L,
    var date: String = "",
    var clientName: String = "",
    var clientNumber: Long = 0L,
    var clientId: Long = 0L,

    var seller: Seller = Seller(),
    var frame: Frame = Frame(),
    var lens: Lens = Lens(),

    var odESF: String = "",
    var odCIL: String = "",
    var odEJE: String = "",
    var odADD: String = "",

    var oiESF: String = "",
    var oiCIL: String = "",
    var oiEJE: String = "",
    var oiADD: String = "",

    var products: List<ProductTable> = listOf(),

    var color: String = "",
    var dp : String = "",

    var abono: Long = 0L,
    var saldo: Long = 0L,

    var total: Long = 0L
)

data class BillDB(
    var lastID: Long = 0L,
    var bills: List<Bill> = listOf(),
)