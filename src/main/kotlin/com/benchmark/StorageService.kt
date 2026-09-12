package com.benchmark

import java.io.File

class StorageService {
    private val baseDir = "/var/app/storage"

    fun readFile(filename: String): ByteArray {
        // Vulnerability: Path traversal via unvalidated user path
        val target = File("$baseDir/$filename")
        return target.readBytes()
    }
}
