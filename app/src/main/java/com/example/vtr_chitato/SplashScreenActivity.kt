package com.example.vtr_chitato

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(2000) // Simulasi loading 2 detik

            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            val intent = if (isLogin) {
                Intent(this@SplashScreenActivity, BaseActivity::class.java)
            } else {
                Intent(this@SplashScreenActivity, AuthActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}