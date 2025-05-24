package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.domain.model.Seller
import com.kerneloso.adam.domain.model.SellersDB
import com.kerneloso.adam.domain.repository.SellersRepository
import kotlinx.coroutines.launch

class SellersViewModel : ViewModel() {

    private val _sellersDB = mutableStateOf(SellersDB())
    val sellersDB: State<SellersDB> get() = _sellersDB

    init {
        loadSellers()
    }

    private fun loadSellers() {
        viewModelScope.launch {
            _sellersDB.value = SellersRepository.loadSellers()
        }
    }

    fun searchSellers(query: String): List<Seller> {
        val normalizedQuery = query.lowercase().split(" ").filter {
            it.isNotBlank()
        }

        return _sellersDB.value.sellers.filter { product ->
            val normalizedName = product.name.lowercase()

            val cleanedName = normalizedName.replace(Regex("[^a-z0-9\\s]") , "")

            val nameWords = cleanedName.split(" ").filter { it.isNotBlank() }

            normalizedQuery.all { searchWord ->
                nameWords.any { nameWord ->
                    nameWord.contains(searchWord)
                }
            }
        }
    }

    fun addSeller(seller: Seller) {
        val current = _sellersDB.value
        val updatedSellersDB = current.copy(
            lastID = current.lastID + 1,
            sellers = current.sellers + seller,
        )
        _sellersDB.value = updatedSellersDB
        viewModelScope.launch {
            SellersRepository.saveSellers(updatedSellersDB)
        }
    }

    fun updateSeller(seller: Seller) {
        val current = _sellersDB.value
        val updatedSellersDB = current.copy(sellers =
            current.sellers.map {
                if ( it.id == seller.id ) {
                    seller
                } else  {
                    it
                }
            }
        )
        _sellersDB.value = updatedSellersDB
        viewModelScope.launch {
            SellersRepository.saveSellers(updatedSellersDB)
        }
    }

    fun deleteSeller(seller: Seller) {
        val current = _sellersDB.value
        val updatedSellersDB = current.copy(
            sellers = current.sellers.filter { it.id != seller.id }
        )
        _sellersDB.value = updatedSellersDB
        viewModelScope.launch {
            SellersRepository.saveSellers(updatedSellersDB)
        }
    }


}