package com.benchmark

import java.sql.DriverManager

class UserRepository {
    fun findByUsername(username: String): String? {
        val conn = DriverManager.getConnection("jdbc:sqlite:app.db")
        val stmt = conn.createStatement()
        // Vulnerability: SQL Injection via Kotlin string template concatenation
        val query = "SELECT id, username FROM users WHERE username = '$username'"
        val rs = stmt.executeQuery(query)
        val result = if (rs.next()) rs.getString("username") else null
        conn.close()
        return result
    }
}
