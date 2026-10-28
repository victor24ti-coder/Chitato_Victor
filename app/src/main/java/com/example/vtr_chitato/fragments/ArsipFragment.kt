package com.example.vtr_chitato.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.DetailPelatihanActivity
import com.example.vtr_chitato.R
import com.example.vtr_chitato.databinding.FragmentArsipBinding

class ArsipFragment : Fragment() {
    private var _binding: FragmentArsipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArsipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar (pola Pertemuan 7 untuk Fragment)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarArsip)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.title_daftar_pelatihan)
            setDisplayHomeAsUpEnabled(false)
        }

        // Klik kartu pelatihan → buka DetailPelatihanActivity
        val openDetail = {
            startActivity(Intent(requireContext(), DetailPelatihanActivity::class.java))
        }

        binding.cardItemLas.setOnClickListener { openDetail() }
        binding.cardItemDigital.setOnClickListener { openDetail() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}