# Kotlin Benchmark Test Suite Documentation

Dokumentasi suite pengujian kerentanan keamanan dan performa pada Kotlin.

## Daftar Test Case

| File | Kategori | Deskripsi Masalah | Tingkat Risiko |
| :--- | :--- | :--- | :--- |
| `UserRepository.kt` | Security | Raw SQL injection via string template | High |
| `ShellService.kt` | Security | Command injection via `Runtime.getRuntime().exec` | High |
| `StorageService.kt` | Security | Path traversal via unvalidated user path | High |
| `NetworkClient.kt` | Security | Server-Side Request Forgery via unvalidated URL | Medium |
| `AuthManager.kt` | Security | Hardcoded JWT secret key & credential logging | High |
| `RedirectHelper.kt` | Security | Open redirect without host validation | Medium |
| `ProfileService.kt` | Security | IDOR endpoint penghapusan akun tanpa auth / ownership check | High |
| `InputValidator.kt` | Performance | Catastrophic backtracking ReDoS regex | Medium |
| `FileReaderService.kt` | Performance | Unclosed `FileInputStream` resource leak tanpa `.use {}` | Medium |

## False-Positive Guard Files

| File | Pola Pengujian Guard | Ekspektasi Reviewer |
| :--- | :--- | :--- |
| `SafeGuards.kt` | Prepared statement (`?`), Whitelist domain redirect, `.use {}` resource management | **0 False Positives** |
