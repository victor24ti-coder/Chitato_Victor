package com.example.vtr_chitato.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.AuthActivity
import com.example.vtr_chitato.R
import com.example.vtr_chitato.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar (pola Pertemuan 7 untuk Fragment)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarProfile)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.nav_profil)
            setDisplayHomeAsUpEnabled(false)
        }

        // Ambil username dari SharedPreferences dan tampilkan
        val sharedPref = requireContext().getSharedPreferences("user_pref", android.content.Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvProfileName.text = username ?: "Pengguna"

        // Tombol Logout
        binding.btnLogoutProfile.setOnClickListener {
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
                .setPositiveButton("Ya") { _, _ ->
                    // Hanya hapus FLAG sesi login, bukan seluruh data akun.
                    // Ini agar tombol biometrik tetap muncul saat login berikutnya
                    // karena username masih tersimpan di SharedPreferences.
                    sharedPref.edit().putBoolean("isLogin", false).apply()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    // Destroy BaseActivity menggunakan requireActivity() (pola Fragment)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}