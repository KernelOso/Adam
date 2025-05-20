package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Seller
import com.kerneloso.adam.domain.model.SellerDB
import com.kerneloso.adam.domain.repository.SellerRepository
import com.kerneloso.adam.domain.repository.SellerRepository.loadSellers
import kotlinx.coroutines.launch

class SellerViewModel : ViewModel() {

    private val _sellerDB = mutableStateOf(SellerDB())
    val sellerDB : State<SellerDB> get() = _sellerDB

    init {
        loadSellers()
    }

    private fun loadSellers(){
        viewModelScope.launch {
            _sellerDB.value = SellerRepository.loadSellers()
        }
    }

    fun addSeller(seller : Seller){
        val current = _sellerDB.value
        val updatedSellerDB = current.copy(
            lastSellerId = current.lastSellerId + 1,
            sellers = current.sellers + seller
        )
        _sellerDB.value = updatedSellerDB
        viewModelScope.launch {
            SellerRepository.saveSellers(updatedSellerDB)
        }
    }

    fun updateSeller(seller: Seller){
        val current = _sellerDB.value
        val updatedSellerDB = current.copy( sellers =
            current.sellers.map {
             if ( it.sellerId == seller.sellerId ){
                 seller
             } else {
                 it
             }
            }
        )
        _sellerDB.value = updatedSellerDB
        viewModelScope.launch {
            SellerRepository.saveSellers(updatedSellerDB)
        }
    }

    fun deleteSeller (seller: Seller){
        val current = _sellerDB.value
        val updatedSellerDB = current.copy(
            sellers = current.sellers.filter {  it.sellerId != seller.sellerId  }
        )
        _sellerDB.value = updatedSellerDB
        viewModelScope.launch {
            SellerRepository.saveSellers(updatedSellerDB)
        }
    }
}