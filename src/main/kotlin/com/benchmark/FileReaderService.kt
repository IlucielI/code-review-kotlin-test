package com.benchmark

import java.io.FileInputStream

class FileReaderService {
    fun countBytes(filePath: String): Int {
        // Performance / Resource Leak: InputStream not closed via use{} or close()
        val stream = FileInputStream(filePath)
        var count = 0
        while (stream.read() != -1) {
            count++
        }
        return count
    }
}
