package com.benchmark

import java.net.URL

class NetworkClient {
    fun fetchUrl(targetUrl: String): String {
        // Vulnerability: SSRF via unvalidated user-supplied URL
        val connection = URL(targetUrl).openConnection()
        connection.connectTimeout = 5000
        return connection.getInputStream().bufferedReader().use { it.readText() }
    }
}
