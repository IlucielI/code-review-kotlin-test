package com.example.demo

import java.io.File
import java.io.FileInputStream
import java.sql.Connection

class VulnerableService(private val connection: Connection) {

    // Bug 1: SQL Injection in string template
    fun findUser(email: String): String? {
        val stmt = connection.createStatement()
        val rs = stmt.executeQuery("SELECT username FROM users WHERE email = '$email'")
        return if (rs.next()) rs.getString("username") else null
    }

    // Bug 2: Path Traversal
    fun getSecretFile(userPath: String): String {
        val file = File("/var/app/data/" + userPath)
        return file.readText()
    }

    // Bug 3: Unclosed FileInputStream
    fun streamBytes(filename: String): ByteArray {
        val fis = FileInputStream(filename)
        return fis.readBytes() // Leak if exception occurs
    }

    // Safe Guard: Parameterized PreparedStatement (MUST NOT be flagged as SQLi)
    fun findUserSafe(email: String): String? {
        val ps = connection.prepareStatement("SELECT username FROM users WHERE email = ?")
        ps.setString(1, email)
        val rs = ps.executeQuery()
        val res = if (rs.next()) rs.getString("username") else null
        rs.close()
        ps.close()
        return res
    }
}
