package com.benchmark

class ShellService {
    fun runDiagnostic(target: String): String? {
        // Vulnerability: Command injection via Runtime.getRuntime().exec with unescaped argument
        val process = Runtime.getRuntime().exec("sh -c ping -c 1 $target")
        return process.inputStream.bufferedReader().readLine()
    }
}
