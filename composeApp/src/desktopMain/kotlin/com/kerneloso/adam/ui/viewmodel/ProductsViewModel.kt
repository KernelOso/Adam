package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Product
import com.kerneloso.adam.domain.model.ProductsDB
import com.kerneloso.adam.domain.repository.ProductsRepository
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {


    private val _productsDB = mutableStateOf(ProductsDB())
    val productsDB: State<ProductsDB> get() = _productsDB

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _productsDB.value = ProductsRepository.loadProducts()
        }
    }

    fun searchProducts(query: String): List<Product> {
        val normalizedQuery = query.lowercase().split(" ").filter {
            it.isNotBlank()
        }

        return _productsDB.value.products.filter { product ->
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

    fun addProduct(product: Product) {
        val current = _productsDB.value
        val updatedProductsDB = current.copy(
            lastID = current.lastID + 1,
            products = current.products + product,
        )
        _productsDB.value = updatedProductsDB
        viewModelScope.launch {
            ProductsRepository.saveProducts(updatedProductsDB)
        }
    }

    fun updateProduct(product: Product) {
        val current = _productsDB.value
        val updatedProductsDB = current.copy(products =
            current.products.map {
                if ( it.id == product.id ) {
                    product
                } else  {
                    it
                }
            }
        )
        _productsDB.value = updatedProductsDB
        viewModelScope.launch {
            ProductsRepository.saveProducts(updatedProductsDB)
        }
    }

    fun deleteProduct(product: Product) {
        val current = _productsDB.value
        val updatedProductsDB = current.copy(
            products = current.products.filter { it.id != product.id }
        )
        _productsDB.value = updatedProductsDB
        viewModelScope.launch {
            ProductsRepository.saveProducts(updatedProductsDB)
        }
    }

}