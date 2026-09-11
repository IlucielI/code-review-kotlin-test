package com.benchmark

import java.io.File
import java.io.FileInputStream
import java.sql.DriverManager

class SafeGuards {
    private val allowedHosts = setOf("example.com", "api.example.com")

    fun safeQuery(username: String): String? {
        // Guard: Prepared statement with parameter binding - NOT SQL injection
        DriverManager.getConnection("jdbc:sqlite:app.db").use { conn ->
            conn.prepareStatement("SELECT id, username FROM users WHERE username = ?").use { stmt ->
                stmt.setString(1, username)
                stmt.executeQuery().use { rs ->
                    return if (rs.next()) rs.getString("username") else null
                }
            }
        }
    }

    fun safeRedirect(url: String): String {
        // Guard: Whitelist host validation - NOT open redirect
        for (host in allowedHosts) {
            if (url.startsWith("https://$host/")) {
                return url
            }
        }
        return "/home"
    }

    fun safeReadFile(path: String): Int {
        // Guard: Kotlin use{} automatic resource cleanup - NOT resource leak
        FileInputStream(File(path)).use { stream ->
            return stream.available()
        }
    }
}
