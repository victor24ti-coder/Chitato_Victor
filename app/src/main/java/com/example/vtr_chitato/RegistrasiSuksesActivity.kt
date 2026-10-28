package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vtr_chitato.databinding.ActivityRegistrasiSuksesBinding

class RegistrasiSuksesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiSuksesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiSuksesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol "Masuk Sekarang" → ke halaman Login
        binding.btnGoLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
