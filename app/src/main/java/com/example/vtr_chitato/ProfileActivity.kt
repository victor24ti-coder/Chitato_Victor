package com.example.vtr_chitato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vtr_chitato.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup Toolbar back button
        setSupportActionBar(binding.toolbarProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbarProfile.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}