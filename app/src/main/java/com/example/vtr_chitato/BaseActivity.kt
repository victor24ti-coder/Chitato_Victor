package com.example.vtr_chitato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.databinding.ActivityBaseBinding
import com.example.vtr_chitato.fragments.ArsipFragment
import com.example.vtr_chitato.fragments.DashboardFragment
import com.example.vtr_chitato.fragments.JobFairFragment
import com.example.vtr_chitato.fragments.ProfileFragment
import com.example.vtr_chitato.fragments.TracerFragment

class BaseActivity : AppCompatActivity() {
    internal lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fix WindowInsets: set bottom=0 agar BottomNav tidak terlalu ke bawah
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Tampilkan DashboardFragment sebagai default saat pertama kali dibuka
        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment())
            binding.bottomNavView.selectedItemId = R.id.nav_dashboard
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> { replaceFragment(DashboardFragment()); true }
                R.id.nav_arsip    -> { replaceFragment(ArsipFragment());    true }
                R.id.nav_jobfair  -> { replaceFragment(JobFairFragment());  true }
                R.id.nav_tracer   -> { replaceFragment(TracerFragment());   true }
                R.id.nav_profile  -> { replaceFragment(ProfileFragment());  true }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            // addToBackStack dinonaktifkan agar tombol back langsung keluar aplikasi
            .commit()
    }
}