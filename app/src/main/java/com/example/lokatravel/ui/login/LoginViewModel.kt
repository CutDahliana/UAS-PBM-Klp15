package com.example.lokatravel.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lokatravel.data.response.LoginResponse
import com.example.lokatravel.ui.profile.DummyAuth

class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email and password are required"
            return
        }

        // Gunakan DummyAuth
        if (DummyAuth.login(email, password)) {
            _loginResponse.value = LoginResponse(
                success = true,
                message = "Login success",
                token = "dummy_token_abc"
            )

        } else {
            _error.value = "Email or password incorrect"
        }
    }
}
