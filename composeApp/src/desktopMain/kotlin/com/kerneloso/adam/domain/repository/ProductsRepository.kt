package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.FramesDB
import com.kerneloso.adam.domain.model.ProductsDB
import com.kerneloso.adam.io.FileUtil

object ProductsRepository {

    private val dbFile = FileUtil.productsFileDB
    private val gson = Gson()

    fun loadProducts(): ProductsDB {
        if (!dbFile.exists()) return ProductsDB()
        return gson.fromJson(dbFile.readText() , ProductsDB::class.java)
    }

    fun saveProducts(db: ProductsDB) {
        dbFile.writeText(gson.toJson(db))
    }

}