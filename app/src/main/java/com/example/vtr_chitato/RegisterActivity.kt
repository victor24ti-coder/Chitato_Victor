package com.example.vtr_chitato

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vtr_chitato.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restore data dari SharedPreferences agar form terisi ulang jika user kembali
        val sp = getSharedPreferences("user_pref", MODE_PRIVATE)
        binding.etName.setText(sp.getString("nama", ""))
        binding.etEmail.setText(sp.getString("email", ""))
        binding.etDob.setText(sp.getString("tanggal_lahir", ""))
        binding.etUsername.setText(sp.getString("username", ""))
        binding.etPassword.setText(sp.getString("password", ""))
        binding.etConfirmPassword.setText(sp.getString("confirm_password", ""))

        // Restore pilihan jenis kelamin
        val savedGender = sp.getString("jenis_kelamin", "")
        when (savedGender) {
            "Laki-laki"  -> binding.rbMale.isChecked = true
            "Perempuan"  -> binding.rbFemale.isChecked = true
        }

        // DatePickerDialog saat field Tanggal Lahir diklik
        binding.etDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year  = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day   = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                binding.etDob.setText(formattedDate)
            }, year, month, day).show()
        }

        // Tombol Selanjutnya
        binding.btnNext.setOnClickListener {
            val name            = binding.etName.text.toString().trim()
            val email           = binding.etEmail.text.toString().trim()
            val dob             = binding.etDob.text.toString().trim()
            val selectedId      = binding.rgGender.checkedRadioButtonId
            val gender          = if (selectedId != -1) {
                                    findViewById<RadioButton>(selectedId).text.toString()
                                  } else ""
            val username        = binding.etUsername.text.toString().trim()
            val password        = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            // Validasi tidak boleh kosong
            if (name.isEmpty() || email.isEmpty() || dob.isEmpty() || gender.isEmpty()
                || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke SharedPreferences dengan key terpisah
            val editor = sp.edit()
            editor.putString("nama",             name)
            editor.putString("email",            email)
            editor.putString("tanggal_lahir",    dob)
            editor.putString("jenis_kelamin",    gender)
            editor.putString("username",         username)
            editor.putString("password",         password)
            editor.putString("confirm_password", confirmPassword)
            editor.apply()

            // Lanjut ke Halaman Verifikasi
            startActivity(Intent(this, VerificationActivity::class.java))
            finish()
        }
    }
}