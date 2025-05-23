package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.LensDB
import com.kerneloso.adam.io.FileUtil

object LensRepository {

    private val dbFile = FileUtil.lensFileDB
    private val gson = Gson()

    fun loadLens(): LensDB {
        if (!dbFile.exists()) return LensDB()
        return gson.fromJson(dbFile.readText() , LensDB::class.java)
    }

    fun saveLens(db: LensDB) {
        dbFile.writeText(gson.toJson(db))
    }

}