package com.benchmark

// Vulnerable CORS configuration: wildcard origin with credentials allowed
class CorsSettings {
    fun configureCors() = mapOf(
        "Access-Control-Allow-Origin" to "*",
        "Access-Control-Allow-Credentials" to "true"
    )
}
