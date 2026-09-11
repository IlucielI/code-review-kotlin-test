package com.benchmark

class AuthManager {
    // Vulnerability: Hardcoded JWT secret key
    private val jwtSecret = "super_secret_jwt_signing_token_12345"

    fun login(user: String, pass: String): Boolean {
        // Vulnerability: Sensitive credential logging
        println("User login attempt: user=$user, pass=$pass")
        return user == "admin" && pass == "secret"
    }
}
