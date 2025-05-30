package com.example.lokatravel.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lokatravel.databinding.FragmentProfileBinding
import com.example.lokatravel.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load user data
        loadUserData()

        // Setup listeners
        setupListeners()
    }

    private fun loadUserData() {
        // Ambil data user dari SharedPreferences yang sama dengan LoginActivity
        val sharedPreferences = requireActivity().getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("user_name", "Guest User")
        val email = sharedPreferences.getString("user_email", "guest@example.com")

        // Set nama dan email ke TextView
        binding.tvName.text = name ?: "Guest User"
        binding.tvEmail.text = email ?: "guest@example.com"
    }

    private fun setupListeners() {
        // Listener untuk tombol logout
        binding.tvLogout.setOnClickListener {
            logout()
        }

        // Listener untuk tombol melaporkan bug
        binding.tvReport.setOnClickListener {
            // Implementasi logika untuk melaporkan bug
            // Misalnya buka email atau form feedback
        }
    }

    private fun logout() {
        // Ambil SharedPreferences yang sama dengan LoginActivity
        val sharedPreferences = requireActivity().getSharedPreferences("LokaTravel", Context.MODE_PRIVATE)

        // Hapus semua data login dari SharedPreferences
        with(sharedPreferences.edit()) {
            remove("is_logged_in")
            remove("user_name")
            remove("user_email")
            remove("login_time")
            apply()
        }

        // Redirect ke halaman login
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onResume() {
        super.onResume()
        // Refresh data user setiap kali fragment muncul
        loadUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}