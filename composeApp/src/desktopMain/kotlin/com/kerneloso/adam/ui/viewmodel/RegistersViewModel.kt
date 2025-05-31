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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productos = buildString {
                    for (producto in bill.products) {
                        appendLine(
                            """
                            <tr>
                              <td>${producto.product.name}</td>
                              <td>${producto.quantity}</td>
                              <td>$${producto.product.price}</td>
                              <td>$${producto.quantity * producto.product.price}</td>
                            </tr>
                            """.trimIndent()
                        )
                    }
                }

                val text = """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                  <meta charset="UTF-8" />
                  <title>Factura #11</title>
                  <style>
                    body {
                      font-family: Arial, sans-serif;
                      background-image: url('https://raw.githubusercontent.com/KernelOso/Adam/refs/heads/main/composeApp/src/desktopMain/composeResources/drawable/logo.png');
                      background-repeat: no-repeat;
                      background-position: center;
                      background-size: 300px;
                      opacity: 0.95;
                      margin: 40px;
                    }
    
                    .factura {
                      background-color: white;
                      padding: 40px;
                      border-radius: 10px;
                      max-width: 800px;
                      margin: auto;
                      box-shadow: 0 0 10px rgba(0,0,0,0.1);
                    }
    
                    h1 {
                      text-align: center;
                      font-size: 28px;
                      margin-bottom: 10px;
                    }
    
                    .section {
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 20px;
                    }
    
                    .datos {
                      font-size: 14px;
                      line-height: 1.6;
                    }
    
                    .titulo {
                      font-weight: bold;
                      margin-top: 20px;
                    }
    
                    table {
                      width: 100%;
                      border-collapse: collapse;
                      margin: 20px 0;
                      font-size: 14px;
                    }
    
                    th, td {
                      border: 1px solid #ccc;
                      padding: 8px;
                      text-align: left;
                    }
    
                    th {
                      background-color: #f2f2f2;
                    }
    
                    .totales {
                      text-align: right;
                      margin-top: 10px;
                    }
    
                    .campo {
                      margin-bottom: 5px;
                    }
                  </style>
                </head>
                <body>
                  <div class="factura">
                    <h1>FACTURA #${bill.id}</h1>
                    <div class="section">
                      <div class="datos">
                        <strong>OPTICA MCA</strong><br />
                        Carrera 12 No. 50-10<br />
                        Tel: 302 297 1601<br />
                        Tel: 321 776 1886<br />
                        IG / FB: @optica_mca
                      </div>
                      <div class="datos">
                        <strong>Fecha:</strong> ${bill.date}<br />
                        <strong>Cliente:</strong>${bill.clientName}<br />
                        <strong>Cel:</strong> ${bill.clientNumber}<br />
                        <strong>CC:</strong>${bill.clientId}<br />
                        <strong>Vendedor:</strong> ${bill.seller.name}
                      </div>
                    </div>
    
                    <div class="titulo">Ojo Izquierdo</div>
                    <div class="datos">
                      ESF: ${bill.oiESF} |
                      CIL: ${bill.oiCIL} |
                      EJE: ${bill.oiEJE} |
                      ADD: ${bill.oiADD}
                    </div>
    
                    <div class="titulo">Ojo Derecho</div>
                    <div class="datos">
                      ESF: ${bill.odESF} |
                      CIL: ${bill.odCIL} |
                      EJE: ${bill.odEJE} |
                      ADD: ${bill.odADD}
                    </div>
    
                    <div class="titulo">Lente</div>
                    <div class="datos">
                      Tipo:${bill.lens.name}<br />
                      Precio: $${longToPrice(bill.lens.price)}
                    </div>
    
                    <div class="titulo">Montura</div>
                    <div class="datos">
                       Tipo:${bill.frame.name}<br />
                      Precio: $${longToPrice(bill.frame.price)}
                    </div>
    
                    <table>
                      <thead>
                        <tr>
                          <th>Nombre producto</th>
                          <th>Cantidad</th>
                          <th>Precio und</th>
                          <th>Precio total</th>
                        </tr>
                      </thead>
                      <tbody>
                        ${productos}
                      </tbody>
                    </table>
    
                    <div class="datos">
                      <div class="campo"><strong>DP:</strong> ${bill.dp}</div>
                      <div class="campo"><strong>Color:</strong> ${bill.color}</div>
                    </div>
    
                    <div class="totales">
                      <p><strong>Total:</strong> $${longToPrice(bill.total)}</p>
                      <p><strong>Abono:</strong> $${longToPrice(bill.abono)}</p>
                      <p><strong>Saldo:</strong> $${longToPrice(bill.saldo)}</p>
                    </div>
                  </div>
                </body>
                </html>
                """.trimIndent()

                val outputName = "${bill.id}.pdf"

                val renderer = ITextRenderer()
                renderer.setDocumentFromString(text)
                renderer.layout()
                FileOutputStream(File(FileUtil.pdfDir, outputName)).use {
                    renderer.createPDF(it)
                }
            }
        }
    }

    fun printBill(bill: Bill) {

        val textToPrint = """
            
OPTICA MCA
=== === === === === ===
Carrera 12 No. 50-10

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
                "- | ${product.quantity} | ${product.product.name}\n    - UND : $${longToPrice(product.product.price)}\n    - $${
                    longToPrice(
                        product.quantity * product.product.price
                    )
                }"
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
        val printServices = PrintServiceLookup.lookupPrintServices(flavor, null)

        val printService = printServices.firstOrNull() ?: throw RuntimeException("No printer found")

        val printJob = printService.createPrintJob()
        val doc = SimpleDoc(textToPrint, flavor, null)

        printJob.print(doc, null)
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
            generatePdf(bill)
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