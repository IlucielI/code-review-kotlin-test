package com.benchmark

class CookieManager {
    // Insecure cookie: session cookie with httpOnly = false and secure = false
    fun buildSessionCookie(token: String): String {
        return "Set-Cookie: session_token=$token; Path=/; HttpOnly=false; Secure=false"
    }
}
