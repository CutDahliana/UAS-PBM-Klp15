package com.example.lokatravel.data.retrofit

data class RegisterRequest(
    val fullname: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

