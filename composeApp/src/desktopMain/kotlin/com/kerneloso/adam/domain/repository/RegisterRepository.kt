package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.BillDB
import com.kerneloso.adam.io.FileUtil

object RegisterRepository {

    private val dbFile = FileUtil.registersFileDB
    private val gson = Gson()

    fun loadRegisters(): BillDB {
        if (!dbFile.exists()) {
            return BillDB()
        } else {
            val result = gson.fromJson(dbFile.readText() , BillDB::class.java)
            return result
        }
    }

    fun saveRegisters(db: BillDB) {
        dbFile.writeText(gson.toJson(db))
    }

    //todo : func generate pdf file

}