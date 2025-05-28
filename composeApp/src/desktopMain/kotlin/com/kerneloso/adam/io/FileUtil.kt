package com.kerneloso.adam.io

import java.io.File
import java.net.URISyntaxException
import java.nio.file.Files

object FileUtil {

    // Directories
    val rootDir: File by lazy { detectRootDir() }
    val dataDir: File by lazy { verifyDir(rootDir.resolve("data")) }
    val pdfDir: File by lazy { verifyDir(dataDir.resolve("pdf")) }

    //Files

    val lensFileDB: File by lazy { verifyJsonFile(dataDir.resolve("lens.json")) }
    val lensFramesFileDB: File by lazy { verifyJsonFile(dataDir.resolve("lensFrames.json")) }
    val productsFileDB: File by lazy { verifyJsonFile(dataDir.resolve("products.json")) }
    val sellersFileDB: File by lazy { verifyJsonFile(dataDir.resolve("sellers.json")) }
    val registersFileDB: File by lazy { verifyJsonFile(dataDir.resolve("registers.json")) }

    private fun detectRootDir(): File {
        return try {
            File(FileUtil::class.java.protectionDomain.codeSource.location.toURI()).parentFile
        } catch (e: URISyntaxException) {
            throw RuntimeException("No se pudo determinar el directorio del ejecutable", e)
        }
    }

    private fun verifyDir(dir: File): File {
        if (!dir.exists()) Files.createDirectories(dir.toPath())
        return dir
    }

    private fun verifyJsonFile(file: File): File {
        if (!file.exists()) file.writeText("[]")
        return file
    }

}