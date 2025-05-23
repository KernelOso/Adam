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
        val current = _productDB.value
        val updatedProductDB = current.copy(
            lastProductId = current.lastProductId + 1,
            products = current.products + product
        )
        _productDB.value = updatedProductDB
        viewModelScope.launch {
            ProductRepository.saveProducts(updatedProductDB)
        }
    }

    fun updateProduct(product: Product) {
        val current = _productDB.value
        val updatedProductDB = current.copy(products =
            current.products.map {
                if ( it.productId == product.productId ) {
                    product
                } else  {
                    it
                }
            }
        )
        _productDB.value = updatedProductDB
        viewModelScope.launch {
            ProductRepository.saveProducts(updatedProductDB)
        }
    }

    fun deleteProduct( product: Product ) {
        val current = _productDB.value
        val updatedProductDB = current.copy(
            products = current.products.filter { it.productId != product.productId }
        )
        _productDB.value = updatedProductDB
        viewModelScope.launch {
            ProductRepository.saveProducts(updatedProductDB)
        }
    }
}