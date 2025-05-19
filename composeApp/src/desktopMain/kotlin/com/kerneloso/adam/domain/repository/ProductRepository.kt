package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.ProductDB
import com.kerneloso.adam.io.FileUtil

object ProductRepository {

    private val dbFile = FileUtil.productsJsonFile
    private val gson = Gson()

    fun loadProducts(): ProductDB {
        if (!dbFile.exists()) return ProductDB()
        return gson.fromJson(dbFile.readText() , ProductDB::class.java)
    }

    fun saveProducts(db: ProductDB) {
        dbFile.writeText(gson.toJson(db))
    }

}