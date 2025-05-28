package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Bill
import com.kerneloso.adam.domain.model.BillDB
import com.kerneloso.adam.domain.repository.RegisterRepository
import com.kerneloso.adam.io.FileUtil
import kotlinx.coroutines.launch
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.File
import java.io.FileOutputStream

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

//    fun searchRegisters(query: String): List<Register> {
//        val normalizedQuery = query.lowercase().split(" ").filter {
//            it.isNotBlank()
//        }
//
//        // TODO :  filtrar por nombre del cliente, fecha, cedula
//
////        return _sellersDB.value.sellers.filter { product ->
////            val normalizedName = product.name.lowercase()
////
////            val cleanedName = normalizedName.replace(Regex("[^a-z0-9\\s]") , "")
////
////            val nameWords = cleanedName.split(" ").filter { it.isNotBlank() }
////
////            normalizedQuery.all { searchWord ->
////                nameWords.any { nameWord ->
////                    nameWord.contains(searchWord)
////                }
////            }
////        }
//    }

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

    fun generatePdf(bill: Bill ) {

        val outputName = "${bill.id}.pdf"

        //Crear plantilla
        val template = """
                            <!DOCTYPE html>
                            <html xmlns="http://www.w3.org/1999/xhtml">
                            <head>
                                <title>Documento</title>
                                <style>
                                    body { font-family: sans-serif; }
                                    h1 { color: navy; }
                                </style>
                            </head>
                            <body>
                                <h1>Hola, ${bill.clientName}</h1>
                                <p>Este PDF fue generado dinámicamente.</p>
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