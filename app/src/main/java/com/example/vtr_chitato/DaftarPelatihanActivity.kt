package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vtr_chitato.databinding.ActivityDaftarPelatihanBinding

class DaftarPelatihanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarPelatihanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarPelatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configure Toolbar WITH back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_daftar_pelatihan)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Click training item -> Navigates to DetailPelatihanActivity
        val openDetailListener = {
            val intent = Intent(this, DetailPelatihanActivity::class.java)
            startActivity(intent)
        }

        binding.cardItemLas.setOnClickListener { openDetailListener() }
        binding.cardItemDigital.setOnClickListener { openDetailListener() }

        // Bottom nav Beranda click
        binding.navBeranda.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}