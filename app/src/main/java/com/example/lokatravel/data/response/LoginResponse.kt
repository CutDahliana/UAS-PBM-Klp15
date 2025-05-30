package com.example.lokatravel.data.response

data class LoginResponse(
	val success: Boolean,
	val message: String,
	val token: String? = null  // optional, bisa null atau dummy
)