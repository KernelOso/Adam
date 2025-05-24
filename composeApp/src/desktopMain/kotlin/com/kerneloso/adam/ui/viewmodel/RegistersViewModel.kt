package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Register
import com.kerneloso.adam.domain.model.RegisterDB
import com.kerneloso.adam.domain.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegistersViewModel : ViewModel() {

    private val _registerDB = mutableStateOf(RegisterDB())
    val registerDB: State<RegisterDB> get() = _registerDB

    init {
        loadRegisters()
    }

    private fun loadRegisters() {
        viewModelScope.launch {
            _registerDB.value = RegisterRepository.loadRegisters()
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

    fun addRegister(register: Register) {
        val current = _registerDB.value
        val updatedRegisterDB = current.copy(
            lastID = current.lastID + 1,
            registers = current.registers + register
        )
        _registerDB.value = updatedRegisterDB
        viewModelScope.launch {
            RegisterRepository.saveRegisters(updatedRegisterDB)
        }
    }

    fun deleteRegister(register: Register) {
        val current = _registerDB.value
        val updatedRegisterDB = current.copy(
            registers = current.registers.filter { it.id != register.id }
        )
        _registerDB.value = updatedRegisterDB
        viewModelScope.launch {
            RegisterRepository.saveRegisters(updatedRegisterDB)
        }
    }


}