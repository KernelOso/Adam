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
import com.kerneloso.adam.util.longToPrice
import kotlinx.coroutines.launch
import org.xhtmlrenderer.pdf.ITextRenderer
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.print.DocFlavor
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.SimpleDoc

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

    fun searchRegisters(id: String = "", date: String = "", clientName: String = ""): List<Bill> {

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


        return searchByName
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

            generatePdf(bill)
        }
    }

    fun generatePdf(bill: Bill) {

        //TODO

//
//          Hmtl ...
//
//        val renderer = ITextRenderer()
//        renderer.setDocumentFromString(template)
//        renderer.layout()
//        FileOutputStream( File( FileUtil.pdfDir , outputName ) ).use {
//            renderer.createPDF(it)
//        }
    }

    fun printBill(bill: Bill) {



        val textToPrint = """
            
OPTICA MCA
=== === === === === ===
Carrera 12 No. 50-12 B

Tel : 302 297 1601
Tel : 321 776 1886

IG / FB @optica_mca
=== === === === === ===
Fecha : ${bill.date}
Factura #${bill.id}
=== === === === === ===
Cliente : ${bill.clientName}
Cel : ${bill.clientNumber}
C.C : ${bill.clientId}
=== === === === === ===
Vendendor : ${bill.seller.name}
=== === === === === ===
Ojo Izquierdo :
ESF : ${bill.oiESF}
CIL : ${bill.oiCIL}
EJE : ${bill.oiEJE}
ADD : ${bill.oiADD}
 
Ojo Derecho :
ESF : ${bill.odESF}
CIL : ${bill.odCIL}
EJE : ${bill.odEJE}
ADD : ${bill.odADD}

=== === === === === ===
Lente : ${bill.lens.name}
Precio : $${longToPrice(bill.lens.price)}
=== === === === === ===
Montura : ${bill.frame.name}
Precio : $${longToPrice(bill.frame.price)}
=== === === === === ===
Productos :
${
bill.products.joinToString("\n") { product ->
    "- | ${product.quantity} | ${product.product.name}\n    - UND : $${longToPrice(product.product.price)}\n    - $${longToPrice(product.quantity * product.product.price)}"
}
}
=== === === === === ===
DP : ${bill.dp}
Color : ${bill.color}
=== === === === === ===
TOTAL : $${longToPrice(bill.total)}

Abono : $${longToPrice(bill.abono)}
Saldo : $${longToPrice(bill.saldo)}










""".trimIndent()

//        // refactorizar para usar las impresoras de windows
//        val devicePath = "/dev/usb/lp0"
//
//        try {
//            FileOutputStream(File(devicePath)).use { output ->
//                output.write(textToPrint.toByteArray())
//                output.flush()
//            }
//            println("Texto enviado correctamente.")
//        } catch (e: Exception) {
//            println("Error al enviar a la impresora: ${e.message}")
//        }
        val flavor = DocFlavor.STRING.TEXT_PLAIN
        val printServices = PrintServiceLookup.lookupPrintServices(flavor , null)

        val printService = printServices.firstOrNull()?: throw RuntimeException("No printer found")

        val printJob = printService.createPrintJob()
        val doc = SimpleDoc(textToPrint , flavor , null)

        printJob.print(doc , null)
    }

    fun openPdf(bill: Bill) {

        val pdfFile = File(FileUtil.pdfDir, "${bill.id}.pdf")

        val desktop = Desktop.getDesktop()

        if (pdfFile.exists()) {
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
        val updatedBillDB = current.copy(
            bills =
                current.bills.map {
                    if (it.id == bill.id) {
                        bill
                    } else {
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