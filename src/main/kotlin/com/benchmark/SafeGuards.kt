package com.benchmark

import java.io.File
import java.io.FileInputStream
import java.sql.DriverManager
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource
import java.io.StringReader

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

    fun safeCookie(token: String): String {
        // Guard: Safe cookie with HttpOnly and Secure flags
        return "Set-Cookie: session_token=$token; Path=/; HttpOnly; Secure; SameSite=Lax"
    }

    fun safeXmlParse(xml: String) {
        // Guard: DocumentBuilderFactory with FEATURE_SECURE_PROCESSING - NOT XXE
        val factory = DocumentBuilderFactory.newInstance()
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        factory.newDocumentBuilder().parse(InputSource(StringReader(xml)))
    }
}
