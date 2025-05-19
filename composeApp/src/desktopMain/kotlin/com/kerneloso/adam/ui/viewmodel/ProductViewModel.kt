package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.domain.model.ProductDB
import com.kerneloso.adam.domain.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _productDB = mutableStateOf(ProductDB())
    val productDB: State<ProductDB> get() = _productDB

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _productDB.value = ProductRepository.loadProducts()
        }
    }

    fun addProduct(product: Product) {
        val updatedProductDB = _productDB.value.copy(
            lastProductId = _productDB.value.lastProductId + 1,
            products = _productDB.value.products + product
        )
        _productDB.value = updatedProductDB
        ProductRepository.saveProducts(updatedProductDB)
    }

    fun deleteProduct( product: Product ) {
        val updatedProductDB = _productDB.value.copy(
            products = _productDB.value.products.filter { it != product }
        )
        _productDB.value = updatedProductDB
        ProductRepository.saveProducts(updatedProductDB)
    }

}