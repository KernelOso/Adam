package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.domain.model.BillDB
import com.kerneloso.adam.domain.repository.LensRepository
import com.kerneloso.adam.domain.repository.RegisterRepository
import com.kerneloso.adam.io.FileUtil
import kotlinx.coroutines.launch
import org.xhtmlrenderer.pdf.ITextRenderer
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegistersViewModel : ViewModel() {

    private val _billDB = mutableStateOf(BillDB())
    val billDB: State<BillDB> get() = _billDB

    init {
        loadRegisters()
    }

    private fun loadRegisters() {
        viewModelScope.launch {
            _billDB.value = RegisterRepository.loadRegisters()
        }
    }

    fun searchRegisters(id: String = "" , date: String = "" , clientName: String = ""): List<Bill> {

        val normalizedId = if (id.all { it.isDigit() }) id else ""
        val normalizedDate = date.trim()
        val searchWords = clientName.lowercase().split(" ").filter { it.isNotBlank() }

        val searchById = if (normalizedId.isNotEmpty()) {
            _billDB.value.bills.filter { bill ->
                bill.id.toString() == id
            }
        } else {
            _billDB.value.bills
        }

        val formatterFull = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val searchByDate = if (normalizedDate.isNotEmpty()) {
            searchById.filter { bill ->
                try {
                    val billDate = LocalDateTime.parse(bill.date, formatterFull)

                    when {
                        // Solo año
                        Regex("^\\d{4}$").matches(normalizedDate) -> {
                            billDate.year.toString() == normalizedDate
                        }

                        // Fecha con hora exacta
                        Regex("^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}$").matches(normalizedDate) -> {
                            val inputDate = LocalDateTime.parse(normalizedDate, formatterFull)
                            billDate == inputDate
                        }

                        // Solo día
                        Regex("^\\d{2}-\\d{2}-\\d{4}$").matches(normalizedDate) -> {
                            val inputDate = LocalDateTime.parse("$normalizedDate 00:00", formatterFull)
                            billDate.toLocalDate() == inputDate.toLocalDate()
                        }

                        else -> true // Si el formato es inválido, no se filtra
                    }

                } catch (e: DateTimeParseException) {
                    true // Si no se puede parsear, no se filtra
                }
            }
        } else {
            searchById
        }

        val searchByName = if (searchWords.isNotEmpty()) {
            searchByDate.filter { bill ->
                val normalizedBillName = bill.clientName.lowercase()
                val cleanedName = normalizedBillName.replace(Regex("[^a-z0-9\\s]"), "")
                val nameWords = cleanedName.split(" ").filter { it.isNotBlank() }

                searchWords.all { searchWord ->
                    nameWords.any { nameWord ->
                        nameWord.contains(searchWord)
                    }
                }
            }
        } else {
            searchByDate
        }


        return  searchByName
    }


    fun addRegister(bill: Bill) {
        val current = _billDB.value
        val updatedRegisterDB = current.copy(
            lastID = current.lastID + 1,
            bills = current.bills + bill
        )
        _billDB.value = updatedRegisterDB
        viewModelScope.launch {
            RegisterRepository.saveRegisters(updatedRegisterDB)

            generatePdf( bill )
        }
    }

    fun generatePdf(bill: Bill) {

        val outputName = "${bill.id}.pdf"

        val logo = "https://raw.githubusercontent.com/KernelOso/Adam/refs/heads/main/composeApp/src/desktopMain/composeResources/drawable/logo.png"

        val template = """
    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Documento</title>
        <style>
            body {
                font-family: sans-serif;
                position: relative;
                margin: 0;
                padding: 20px;
                min-height: 100vh;
                background: url('$logo') no-repeat center center;
                background-size: 300px 300px;
                opacity: 0.1;
            }
            .content {
                position: relative;
                opacity: 1;
            }
            h1 {
                color: navy;
            }
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <div class="content">
            <h1>Hola, ${bill.clientName}</h1>
            <p>Este PDF fue generado dinámicamente.</p>

            <h2>Información del Cliente</h2>
            <ul>
                <li><b>ID:</b> ${bill.id}</li>
                <li><b>Fecha:</b> ${bill.date}</li>
                <li><b>Cliente:</b> ${bill.clientName}</li>
                <li><b>Número Cliente:</b> ${bill.clientNumber}</li>
                <li><b>ID Cliente:</b> ${bill.clientId}</li>
            </ul>

            <h2>Vendedor</h2>
            <ul>
                <li><b>Nombre:</b> ${bill.seller.name}</li>
            </ul>

            <h2>Armazón</h2>
            <ul>
                <li><b>Modelo:</b> ${bill.frame.name}</li>
                <li><b>Precio:</b> ${bill.frame.price}</li>
                <!-- Más propiedades Frame -->
            </ul>

            <h2>Lentes</h2>
            <ul>
                <li><b>Tipo:</b> ${bill.lens.name}</li>
                <li><b>Tipo:</b> ${bill.lens.price}</li>
                <!-- Más propiedades Lens -->
            </ul>

            <h2>Medidas OD</h2>
            <ul>
                <li><b>ESF:</b> ${bill.odESF}</li>
                <li><b>CIL:</b> ${bill.odCIL}</li>
                <li><b>EJE:</b> ${bill.odEJE}</li>
                <li><b>ADD:</b> ${bill.odADD}</li>
            </ul>

            <h2>Medidas OI</h2>
            <ul>
                <li><b>ESF:</b> ${bill.oiESF}</li>
                <li><b>CIL:</b> ${bill.oiCIL}</li>
                <li><b>EJE:</b> ${bill.oiEJE}</li>
                <li><b>ADD:</b> ${bill.oiADD}</li>
            </ul>

            <h2>Productos</h2>
            <table>
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    ${
            bill.products.joinToString(separator = "") { product ->
                """
                            <tr>
                                <td>${product.product.name}</td>
                                <td>${product.quantity}</td>
                                <td>${product.product.price}</td>
                            </tr>
                            """
            }
        }
                </tbody>
            </table>

            <h2>Datos Adicionales</h2>
            <ul>
                <li><b>Color:</b> ${bill.color}</li>
                <li><b>DP:</b> ${bill.dp}</li>
            </ul>

            <h2>Finanzas</h2>
            <ul>
                <li><b>Abono:</b> ${bill.abono}</li>
                <li><b>Saldo:</b> ${bill.saldo}</li>
                <li><b>Total:</b> ${bill.total}</li>
            </ul>
        </div>
    </body>
    </html>
""".trimIndent()


        val renderer = ITextRenderer()
        renderer.setDocumentFromString(template)
        renderer.layout()
        FileOutputStream( File( FileUtil.pdfDir , outputName ) ).use {
            renderer.createPDF(it)
        }
    }

    fun openPdf(bill: Bill) {

        val pdfFile = File(FileUtil.pdfDir ,"${bill.id}.pdf" )

        val desktop = Desktop.getDesktop()

        if ( pdfFile.exists() ) {
            //solo abrir
            desktop.open(pdfFile)
        } else {
            //crear y abrir
            generatePdf(bill)
            desktop.open(pdfFile)
        }

    }

    fun updateBill(bill: Bill) {
        val current = _billDB.value
        val updatedBillDB = current.copy(bills =
            current.bills.map {
                if ( it.id == bill.id ) {
                    bill
                } else  {
                    it
                }
            }
        )
        _billDB.value = updatedBillDB
        viewModelScope.launch {
            RegisterRepository.saveRegisters(updatedBillDB)
        }
    }

    fun deleteRegister(bill: Bill) {
        val current = _billDB.value
        val updatedRegisterDB = current.copy(
            bills = current.bills.filter { it.id != bill.id }
        )
        _billDB.value = updatedRegisterDB
        viewModelScope.launch {
            RegisterRepository.saveRegisters(updatedRegisterDB)
        }
    }


}