package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.ProductsDB
import com.kerneloso.adam.domain.model.SellersDB
import com.kerneloso.adam.io.FileUtil

object SellersRepository {

    private val dbFile = FileUtil.sellersFileDB
    private val gson = Gson()

    fun loadSellers(): SellersDB {
        if (!dbFile.exists()) return SellersDB()
        return gson.fromJson(dbFile.readText() , SellersDB::class.java)
    }

    fun saveSellers(db: SellersDB) {
        dbFile.writeText(gson.toJson(db))
    }

}