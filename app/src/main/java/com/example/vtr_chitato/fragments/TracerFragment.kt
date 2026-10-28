package com.example.vtr_chitato.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.R
import com.example.vtr_chitato.databinding.FragmentTracerBinding

class TracerFragment : Fragment() {
    private var _binding: FragmentTracerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTracerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar (pola Pertemuan 7 untuk Fragment)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarTracer)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.module_tracer)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}