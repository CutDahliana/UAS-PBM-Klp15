package com.example.lokatravel.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lokatravel.data.response.RegisterResponse
import com.example.lokatravel.ui.profile.DummyAuth

class RegisterViewModel : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse>
        get() = _registerResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    /**
     * Fungsi untuk mendaftarkan user baru.
     * Melakukan pengecekan sederhana sebelum memanggil DummyAuth.
     */
    fun registerUser(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _error.postValue("All fields are required")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _error.postValue("Invalid email format")
            return
        }

        if (password.length < 8) {
            _error.postValue("Password must be at least 8 characters")
            return
        }

        val success = DummyAuth.register(username, email, password)
        if (success) {
            _registerResponse.postValue(
                RegisterResponse(
                    success = true,
                    message = "Registration successful"
                )
            )
        } else {
            _error.postValue("Registration failed")
        }
    }
}
