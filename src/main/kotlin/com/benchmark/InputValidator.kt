package com.benchmark

class InputValidator {
    // Performance Bug: Catastrophic backtracking regex prone to ReDoS
    private val redosRegex = Regex("^([a-zA-Z0-9]+)+$")

    fun isValid(input: String): Boolean {
        return redosRegex.matches(input)
    }
}
