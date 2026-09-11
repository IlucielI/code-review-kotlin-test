# Kotlin (JVM / Mobile Backend) Benchmark Test Suite

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9%2B-7F52FF.svg?logo=kotlin)](https://kotlinlang.org/)
[![Target Platform](https://img.shields.io/badge/Platform-JVM%20%2F%20Android%20Backend-purple.svg)](#test-case-matrix)
[![Benchmark Category](https://img.shields.io/badge/Benchmark-Security%20%26%20Idiomatic%20Patterns-blue.svg)](#test-case-matrix)
[![Safe Guard](https://img.shields.io/badge/False%20Positive%20Guard-Active-brightgreen.svg)](#anti-false-positive-guard-file)

Benchmark test suite for automated code review engines on Kotlin (JVM, Ktor, Spring Kotlin, and Android Backend) applications. This repository contains intentional security vulnerabilities, unclosed Kotlin stream leaks, non-namespaced logging exposures, and idiomatic safe-guard patterns.

---

## 🎯 Benchmark Purpose

1. **Kotlin Language Awareness:** Accurately detects string template SQL injection (`"""SELECT ... $param"""`), unvalidated URL connections, and unescaped command execution.
2. **Resource Management & Idioms:** Identifies stream leaks where I/O streams are opened without Kotlin's idiomatic `.use { }` block or explicit `close()`.
3. **Logging & Credential Security:** Catches standalone `println(...)` and logging frameworks exposing passwords and tokens in plaintext.
4. **Zero False Positives:** Validates that parameterized SQL statements, URL host whitelists, and `.use { }` scopes produce **0 false positives**.

---

## 📋 Test Case Matrix

### 🔴 Security Vulnerabilities

| File | Issue / Vulnerability | Type | CWE | Severity | Expected |
| :--- | :--- | :--- | :--- | :---: | :---: |
| `UserRepository.kt` | Raw SQL Injection via Kotlin string template (`$username`) | Injection | CWE-89 | High | **BLOCKING** |
| `ShellService.kt` | Command Injection via `Runtime.getRuntime().exec("sh -c " + cmd)` | RCE | CWE-78 | High | **BLOCKING** |
| `StorageService.kt` | Path Traversal via unvalidated `File(baseDir, filename)` | File Security | CWE-22 | High | **BLOCKING** |
| `NetworkClient.kt` | Server-Side Request Forgery via `URL(targetUrl).openConnection()` | Network Security | CWE-918 | Medium | **BLOCKING** |
| `AuthManager.kt` | Hardcoded Secret Key & Plaintext Credential Logging (`println`) | Information Disclosure | CWE-798 / CWE-532 | High | **BLOCKING** |
| `RedirectHelper.kt` | Open Redirect without destination host validation | Redirection | CWE-601 | Medium | **BLOCKING** |
| `ProfileService.kt` | IDOR on account deletion without ownership check | Broken Access Control | CWE-639 | High | **BLOCKING** |

### ⚡ Performance & Resource Leaks

| File | Issue | Type | Severity | Expected |
| :--- | :--- | :--- | :---: | :---: |
| `FileReaderService.kt` | Unclosed `FileInputStream` resource leak without `.use { }` | Resource Leak | Medium | **NON-BLOCKING** |
| `InputValidator.kt` | Catastrophic Backtracking Regular Expression (ReDoS) | Algorithmic Complexity | Medium | **NON-BLOCKING** |

---

## 🛡️ Anti-False-Positive Guard File

| File | Safe Pattern Implemented | Expected Reviewer Result |
| :--- | :--- | :---: |
| `SafeGuards.kt` | Parameterized JDBC statements (`?`), strict domain whitelist redirect, idiomatic `.use { }` automatic resource closure | **0 False Positives** (Clean) |

---

## 🚀 How to Run the Benchmark

```bash
# View PR on GitHub
gh pr view 1 --web

# Trigger Review via API
curl -X POST http://localhost:8081/api/v1/review/trigger \
  -H "Content-Type: application/json" \
  -d '{
    "repository": "IlucielI/code-review-kotlin-test",
    "pull_request_id": 1
  }'
```

---

## 📊 Benchmark Validation Results

- **Detection Rate:** 10 / 10 (100%)
- **False Positive Rate:** 0 / 1 (`SafeGuards.kt` completely passed)
- **False Negative Rate:** 0%
