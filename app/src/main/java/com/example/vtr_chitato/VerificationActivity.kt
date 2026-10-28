package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtr_chitato.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Baca SEMUA data dari SharedPreferences (bukan dari Intent)
        val sp              = getSharedPreferences("user_pref", MODE_PRIVATE)
        val nama            = sp.getString("nama", "") ?: ""
        val email           = sp.getString("email", "") ?: ""
        val dob             = sp.getString("tanggal_lahir", "") ?: ""
        val gender          = sp.getString("jenis_kelamin", "") ?: ""
        val username        = sp.getString("username", "") ?: ""
        val password        = sp.getString("password", "") ?: ""
        val confirmPassword = sp.getString("confirm_password", "") ?: ""

        // Tampilkan seluruh isian ke UI
        binding.tvNama.text            = nama
        binding.tvEmail.text           = email
        binding.tvDob.text             = dob
        binding.tvGender.text          = gender
        binding.tvUsername.text        = username
        binding.tvPassword.text        = password
        binding.tvConfirmPassword.text = confirmPassword

        // Tombol Submit – validasi data dari SP
        binding.btnSubmit.setOnClickListener {
            // Cek satu per satu, tampilkan pesan spesifik jika kosong
            if (nama.isEmpty()) {
                Toast.makeText(this, "Error: Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "Error: Email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dob.isEmpty()) {
                Toast.makeText(this, "Error: Tanggal Lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (gender.isEmpty()) {
                Toast.makeText(this, "Error: Jenis Kelamin tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                Toast.makeText(this, "Error: Username tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Error: Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Error: Konfirmasi Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Error: Password dan Konfirmasi Password tidak cocok!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validasi lolos → ke halaman Registrasi Berhasil
            startActivity(Intent(this, RegistrasiSuksesActivity::class.java))
            finish()
        }

        // Tombol Kembali – kembali ke RegisterActivity (data SP tetap ada)
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}
