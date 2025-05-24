package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.RegisterDB
import com.kerneloso.adam.io.FileUtil

object RegisterRepository {

    private val dbFile = FileUtil.registersFileDB
    private val gson = Gson()

    fun loadRegisters(): RegisterDB {
        if (!dbFile.exists()) {
            return RegisterDB()
        } else {
            val result = gson.fromJson(dbFile.readText() , RegisterDB::class.java)
            return result
        }
    }

    fun saveRegisters(db: RegisterDB) {
        dbFile.writeText(gson.toJson(db))
    }

    //todo : func generate pdf file

}