package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.vtr_chitato.databinding.ActivityAuthBinding

/**
 * AuthActivity — Login screen dengan dua metode autentikasi:
 * 1. Login manual (username + password) via SharedPreferences
 * 2. Login biometrik (sidik jari / wajah) via AndroidX BiometricPrompt
 *
 * Referensi Biometric:
 * - Android Developers: https://developer.android.com/training/sign-in/biometric-auth
 * - YouTube (Philipp Lackner): https://www.youtube.com/watch?v=_dCRQ9wta-I
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Periksa apakah perangkat mendukung biometrik ---
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate = biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)

        // Tampilkan tombol biometrik hanya jika:
        // (1) Perangkat mendukung biometrik, DAN
        // (2) Sudah ada akun yang tersimpan (sudah pernah register)
        val sp = getSharedPreferences("user_pref", MODE_PRIVATE)
        val hasAccount = sp.getString("username", "").orEmpty().isNotEmpty()

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS && hasAccount) {
            binding.btnBiometric.visibility = android.view.View.VISIBLE
        } else {
            binding.btnBiometric.visibility = android.view.View.GONE
        }

        // --- Tombol Login Manual ---
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val spUsername = sp.getString("username", "") ?: ""
            val spPassword = sp.getString("password", "") ?: ""

            // rule1: username == password (akun default awal)
            val rule1 = username.isNotEmpty() && username == password
            // rule2: cocok dengan akun tersimpan di SharedPreferences
            val rule2 = username.isNotEmpty() && password.isNotEmpty()
                    && username == spUsername && password == spPassword

            if (rule1 || rule2) {
                sp.edit().putBoolean("isLogin", true).apply()
                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login gagal! Username atau password salah.", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Tombol Biometrik → tampilkan BiometricPrompt ---
        binding.btnBiometric.setOnClickListener {
            showBiometricPrompt()
        }

        // --- Teks "Daftar" → ke halaman Registrasi ---
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * Menampilkan BiometricPrompt untuk autentikasi sidik jari / wajah.
     * Menggunakan PromptInfo yang dikonfigurasi dengan judul dan subtitel
     * yang sesuai konteks aplikasi Disnaker.
     */
    private fun showBiometricPrompt() {
        // Executor untuk callback pada main thread
        val executor = ContextCompat.getMainExecutor(this)

        // Callback hasil autentikasi biometrik
        val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Autentikasi berhasil → set isLogin = true dan masuk ke BaseActivity
                getSharedPreferences("user_pref", MODE_PRIVATE)
                    .edit().putBoolean("isLogin", true).apply()
                Toast.makeText(
                    this@AuthActivity,
                    "✅ Login biometrik berhasil!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@AuthActivity, BaseActivity::class.java))
                finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Sidik jari tidak dikenali (belum 5x gagal → belum lockout)
                Toast.makeText(
                    this@AuthActivity,
                    "Sidik jari tidak dikenali. Coba lagi.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Error fatal (mis. terlalu banyak percobaan gagal, pengguna membatalkan)
                if (errorCode != BiometricPrompt.ERROR_USER_CANCELED &&
                    errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(
                        this@AuthActivity,
                        "Kesalahan biometrik: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Buat instance BiometricPrompt dengan executor dan callback
        val biometricPrompt = BiometricPrompt(this, executor, biometricCallback)

        // Konfigurasi tampilan dialog biometrik
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login SIMANDAT Disnaker")
            .setSubtitle("Gunakan sidik jari atau pengenalan wajah untuk masuk")
            .setDescription("Letakkan jari Anda pada sensor untuk memverifikasi identitas.")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}