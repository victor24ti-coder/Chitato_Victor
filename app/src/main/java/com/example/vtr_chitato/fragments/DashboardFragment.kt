package com.example.vtr_chitato.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.DaftarPelatihanActivity
import com.example.vtr_chitato.R
import com.example.vtr_chitato.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarDashboard)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.title_dashboard)
            setDisplayHomeAsUpEnabled(false)
        }

        // Klik modul Pelatihan → buka DaftarPelatihanActivity
        binding.cardModulPelatihan.setOnClickListener {
            startActivity(Intent(requireContext(), DaftarPelatihanActivity::class.java))
        }

        // Klik modul Job Fair → navigasi ke tab Job Fair di bottom nav
        binding.cardModulJobFair.setOnClickListener {
            requireActivity().let { activity ->
                if (activity is com.example.vtr_chitato.BaseActivity) {
                    activity.binding.bottomNavView.selectedItemId =
                        com.example.vtr_chitato.R.id.nav_jobfair
                }
            }
        }

        // Klik modul Tracer → navigasi ke tab Tracer di bottom nav
        binding.cardModulTracer.setOnClickListener {
            requireActivity().let { activity ->
                if (activity is com.example.vtr_chitato.BaseActivity) {
                    activity.binding.bottomNavView.selectedItemId =
                        com.example.vtr_chitato.R.id.nav_tracer
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}