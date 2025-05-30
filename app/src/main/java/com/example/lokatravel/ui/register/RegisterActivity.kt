package com.example.lokatravel.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lokatravel.MainActivity
import com.example.lokatravel.ui.login.LoginActivity
import com.example.lokatravel.R
import com.example.lokatravel.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // Setup show password checkboxes
        setupPasswordVisibility()

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPass.text.toString().trim()

            // Validasi manual terlebih dahulu
            if (validateInput(name, email, password, confirmPassword)) {
                // Jika validasi lokal berhasil, coba register dengan ViewModel
                // Tapi juga simpan ke SharedPreferences sebagai backup
                saveUserToSharedPreferences(name, email, password)

                // Panggil ViewModel untuk register (jika menggunakan API)
                viewModel.registerUser(name, email, password)
            }
        }

        viewModel.registerResponse.observe(this) { response ->
            response?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                // Navigasi ke LoginActivity setelah register sukses
                navigateToLogin()
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                // Jika API gagal, tapi data sudah disimpan di SharedPreferences
                // User masih bisa login dengan data lokal
                Toast.makeText(this, "Register berhasil disimpan secara lokal. $it", Toast.LENGTH_LONG).show()
                navigateToLogin()
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        // Reset error
        binding.etName.error = null
        binding.etEmail.error = null
        binding.etPassword.error = null
        binding.etConfirmPass.error = null

        var isValid = true

        // Validasi nama
        if (name.isEmpty()) {
            binding.etName.error = getString(R.string.ERROR_NAME_EMPTY)
            isValid = false
        }

        // Validasi email
        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.ERROR_EMAIL_EMPTY)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Format email tidak valid"
            isValid = false
        } else if (isEmailAlreadyRegistered(email)) {
            binding.etEmail.error = "Email sudah terdaftar"
            isValid = false
        }

        // Validasi password
        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.ERROR_PASSWORD_EMPTY)
            isValid = false
        } else if (password.length < 6) {
            binding.etPassword.error = "Password minimal 6 karakter"
            isValid = false
        }

        // Validasi konfirmasi password
        if (confirmPassword.isEmpty()) {
            binding.etConfirmPass.error = "Konfirmasi password tidak boleh kosong"
            isValid = false
        } else if (password != confirmPassword) {
            binding.etConfirmPass.error = getString(R.string.ERROR_PASSWORD_MISMATCH)
            isValid = false
        }

        return isValid
    }

    private fun isEmailAlreadyRegistered(email: String): Boolean {
        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)
        val allEntries = sharedPreferences.all
        val emailEntries = allEntries.filterKeys { it.startsWith("user_email_") }

        return emailEntries.values.any { it == email }
    }

    private fun saveUserToSharedPreferences(name: String, email: String, password: String) {
        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)

        // Generate unique ID untuk user
        val userId = System.currentTimeMillis().toString()

        with(sharedPreferences.edit()) {
            putString("user_name_$userId", name)
            putString("user_email_$userId", email)
            putString("user_password_$userId", password)
            putLong("user_register_time_$userId", System.currentTimeMillis())
            apply()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    // Method untuk mengatur visibility password
    private fun setupPasswordVisibility() {
        // Show/Hide Password
        binding.cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // Move cursor to end
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }

        // Show/Hide Confirm Password
        binding.cbShowConfirmPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etConfirmPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etConfirmPass.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // Move cursor to end
            binding.etConfirmPass.setSelection(binding.etConfirmPass.text?.length ?: 0)
        }
    }
}