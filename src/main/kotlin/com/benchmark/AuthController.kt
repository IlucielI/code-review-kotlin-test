package com.benchmark

class AuthController {
    // Missing rate limiting on sensitive authentication route
    fun login(username: String, pass: String): String {
        return "auth-ok:$username"
    }
}
