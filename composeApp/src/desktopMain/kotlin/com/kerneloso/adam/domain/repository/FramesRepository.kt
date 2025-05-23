package com.kerneloso.adam.domain.repository

import com.google.gson.Gson
import com.kerneloso.adam.domain.model.FramesDB
import com.kerneloso.adam.domain.model.LensDB
import com.kerneloso.adam.io.FileUtil

object FramesRepository {

    private val dbFile = FileUtil.lensFramesFileDB
    private val gson = Gson()

    fun loadFrames(): FramesDB {
        if (!dbFile.exists()) return FramesDB()
        return gson.fromJson(dbFile.readText() , FramesDB::class.java)
    }

    fun saveFrames(db: FramesDB) {
        dbFile.writeText(gson.toJson(db))
    }


}