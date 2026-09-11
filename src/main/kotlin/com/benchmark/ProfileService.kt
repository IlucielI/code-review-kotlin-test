package com.benchmark

class ProfileService {
    private val profileStore = mutableMapOf(1L to "User Alice", 2L to "User Bob")

    fun deleteProfile(userId: Long): Boolean {
        // Vulnerability: IDOR missing authentication and ownership check
        return profileStore.remove(userId) != null
    }
}
