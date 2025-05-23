package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.Seller
import com.kerneloso.adam.domain.model.SellerDB
import com.kerneloso.adam.io.FileUtil

object SellerRepository {

    private val dbFile = FileUtil.sellersJsonFile
    private val gson = Gson()

    fun loadSellers() : SellerDB {
        if (!dbFile.exists()) return SellerDB()
        return gson.fromJson(dbFile.readText() , SellerDB::class.java)
    }

    fun saveSellers(db : SellerDB){
        dbFile.writeText(gson.toJson(db))
    }

}