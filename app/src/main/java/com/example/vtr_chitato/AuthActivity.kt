package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtr_chitato.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val sp = getSharedPreferences("user_pref", MODE_PRIVATE)
            val spUsername = sp.getString("username", "") ?: ""
            val spPassword = sp.getString("password", "") ?: ""

            val rule1 = username.isNotEmpty() && username == password
            val rule2 = username.isNotEmpty() && password.isNotEmpty()
                    && username == spUsername && password == spPassword

            if (rule1 || rule2) {
                sp.edit().putBoolean("isLogin", true).apply()
                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login gagal! Username atau password salah.", Toast.LENGTH_SHORT).show()
            }
        }

        // Teks "Daftar" → ke halaman Registrasi
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}