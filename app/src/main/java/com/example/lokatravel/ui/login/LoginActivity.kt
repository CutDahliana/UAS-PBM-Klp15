package com.example.lokatravel.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lokatravel.MainActivity
import com.example.lokatravel.R
import com.example.lokatravel.databinding.ActivityLoginBinding
import com.example.lokatravel.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // DUMMY DATA - Fallback jika tidak ada data register
    private val dummyUsers = listOf(
        DummyUser("test@gmail.com", "123456", "Test User"),
        DummyUser("admin@gmail.com", "admin123", "Admin User"),
        DummyUser("user@gmail.com", "password", "Regular User")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek status login
        checkLoginStatus()

        // Setup UI
        setupRegisterText()
        setupLoginButton()
        setupPasswordToggle()
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            Log.d("LoginActivity", "User already logged in, navigating to MainActivity")
            navigateToMainActivity()
        }
    }

    private fun setupRegisterText() {
        val tvRegister = binding.tvRegister
        val tvFirstPart = getString(R.string.INFO_REGISTER_TEXT)
        val tvSecondPart = " " + getString(R.string.REGISTER_TEXT)
        val registerSpannable = generateSpannableString(tvFirstPart, tvSecondPart)
        tvRegister.text = registerSpannable
        tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            performLogin()
        }
    }

    private fun setupPasswordToggle() {
        binding.cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // Pindahkan cursor ke akhir
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }
    }

    private fun performLogin() {
        // Tampilkan loading
        showLoading(true)

        // Ambil input
        val email = getEmailInput()
        val password = getPasswordInput()

        Log.d("LoginActivity", "Email: '$email', Password: '$password'")

        // Validasi input kosong
        if (email.isEmpty()) {
            showLoading(false)
            Toast.makeText(this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            showLoading(false)
            Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulasi delay network (opsional)
        binding.root.postDelayed({
            checkCredentials(email, password)
        }, 1000) // 1 detik delay
    }

    private fun getEmailInput(): String {
        return try {
            binding.etEmail.text?.toString()?.trim() ?: ""
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error getting email", e)
            ""
        }
    }

    private fun getPasswordInput(): String {
        return try {
            binding.etPassword.text?.toString()?.trim() ?: ""
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error getting password", e)
            ""
        }
    }

    private fun checkCredentials(email: String, password: String) {
        Log.d("LoginActivity", "Checking credentials for: $email")

        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)

        // Pertama cek data dari register
        val registeredUsers = getAllRegisteredUsers()
        val matchedRegisteredUser = registeredUsers.find {
            it.email.equals(email, ignoreCase = true) && it.password == password
        }

        // Jika tidak ditemukan di data register, cek dummy data
        val matchedUser = matchedRegisteredUser ?: dummyUsers.find {
            it.email.equals(email, ignoreCase = true) && it.password == password
        }

        showLoading(false)

        if (matchedUser != null) {
            // Login berhasil
            Log.d("LoginActivity", "Login successful for: ${matchedUser.email}")
            Toast.makeText(this, "Login berhasil! Selamat datang ${matchedUser.name}", Toast.LENGTH_SHORT).show()

            // Simpan status login
            saveLoginStatus(matchedUser)

            // Navigate ke MainActivity
            navigateToMainActivity()
        } else {
            // Login gagal
            Log.d("LoginActivity", "Login failed for: $email")
            Toast.makeText(this, "Email atau password salah!\n\nCoba:\nEmail: test@gmail.com\nPassword: 123456", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAllRegisteredUsers(): List<DummyUser> {
        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)
        val users = mutableListOf<DummyUser>()

        // Ambil semua data user yang terdaftar
        // Asumsi data register disimpan dengan key pattern: "user_email_X", "user_password_X", "user_name_X"
        val allEntries = sharedPreferences.all
        val emailEntries = allEntries.filterKeys { it.startsWith("user_email_") }

        emailEntries.forEach { (emailKey, email) ->
            val userId = emailKey.substringAfter("user_email_")
            val password = sharedPreferences.getString("user_password_$userId", null)
            val name = sharedPreferences.getString("user_name_$userId", null)

            if (email is String && password != null && name != null) {
                users.add(DummyUser(email, password, name))
            }
        }

        return users
    }

    private fun saveLoginStatus(user: DummyUser) {
        val sharedPreferences = getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", true)
            putString("user_email", user.email)
            putString("user_name", user.name)
            putLong("login_time", System.currentTimeMillis())
            apply()
        }
        Log.d("LoginActivity", "Login status saved for: ${user.email}")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading

        if (isLoading) {
            binding.btnLogin.text = "Loading..."
        } else {
            binding.btnLogin.text = getString(R.string.LOGIN_TEXT)
        }
    }

    private fun navigateToMainActivity() {
        Log.d("LoginActivity", "Navigating to MainActivity")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun generateSpannableString(firstPart: String, secondPart: String): Spannable {
        val spannable = SpannableString(firstPart + secondPart)
        val boldStyleSpan = StyleSpan(Typeface.BOLD)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToRegisterActivity()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        val blueColor = ContextCompat.getColor(this, R.color.blue)
        val startSecondPart = firstPart.length
        val endSecondPart = spannable.length

        spannable.setSpan(
            clickableSpan,
            startSecondPart,
            endSecondPart,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(blueColor),
            startSecondPart,
            endSecondPart,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            boldStyleSpan,
            startSecondPart,
            endSecondPart,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannable
    }
}

// Data class untuk user
data class DummyUser(
    val email: String,
    val password: String,
    val name: String
)