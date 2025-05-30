package com.example.lokatravel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lokatravel.databinding.ActivityMainBinding
import com.example.lokatravel.ui.home.HomeFragment
import com.example.lokatravel.ui.news.NewsFragment
import com.example.lokatravel.ui.profile.ProfileFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragmentId: Int = R.id.beranda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.beranda -> {
                    currentFragmentId = R.id.beranda
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.news -> {
                    currentFragmentId = R.id.news
                    replaceFragment(NewsFragment())
                    true
                }
                R.id.profile -> {
                    currentFragmentId = R.id.profile
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState != null) {
            currentFragmentId = savedInstanceState.getInt("currentFragmentId", R.id.beranda)
        }

        when (currentFragmentId) {
            R.id.beranda -> replaceFragment(HomeFragment())
            R.id.profile -> replaceFragment(ProfileFragment())
            R.id.news -> replaceFragment(NewsFragment())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentFragmentId", currentFragmentId)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}