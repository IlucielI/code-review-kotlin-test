package com.benchmark

class RedirectHelper {
    fun getRedirectLocation(targetUrl: String): String {
        // Vulnerability: Open redirect without domain validation
        return targetUrl
    }
}
