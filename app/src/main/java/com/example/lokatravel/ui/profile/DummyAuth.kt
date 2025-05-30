package com.example.lokatravel.ui.profile

object DummyAuth {
    fun login(username: String, password: String): Boolean {
        return username == "admin" && password == "1234"
    }

    fun register(username: String, email: String, password: String): Boolean {
        // Simulasi registrasi berhasil selalu
        return true
    }
}
