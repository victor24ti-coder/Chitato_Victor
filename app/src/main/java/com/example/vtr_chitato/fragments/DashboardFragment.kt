package com.example.vtr_chitato.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vtr_chitato.DaftarPelatihanActivity
import com.example.vtr_chitato.R
import com.example.vtr_chitato.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

/**
 * DashboardFragment — Beranda utama SIMANDAT Disnaker.
 * Menampilkan statistik ringkas dan modul layanan.
 *
 * Fitur Baru: MPAndroidChart — Pie Chart Tracer Study Alumni
 * Library: com.github.PhilJay:MPAndroidChart:v3.1.0
 * Referensi:
 * - GitHub: https://github.com/PhilJay/MPAndroidChart
 * - YouTube (Coding with Mitch): https://www.youtube.com/watch?v=3JGLM1mqUBg
 */
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

        // ── Fitur Baru: Setup Pie Chart MPAndroidChart ──────────────────────────
        setupTracerPieChart(binding.chartTracerStudy)
    }

    /**
     * Mengkonfigurasi dan mengisi PieChart dengan data Tracer Study alumni.
     * Data menunjukkan status alumni: Bekerja, Wirausaha, Belum Bekerja.
     *
     * Menggunakan MPAndroidChart v3.1.0 dari JitPack (com.github.PhilJay).
     * Sumber: https://github.com/PhilJay/MPAndroidChart
     */
    private fun setupTracerPieChart(chart: PieChart) {
        // Data Tracer Study (dalam persentase dari 4.120 total alumni)
        // Sumber data: contoh data dashboard Disnaker Riau
        val entries = listOf(
            PieEntry(62f, "Bekerja"),      // 62% alumni bekerja
            PieEntry(16f, "Wirausaha"),    // 16% alumni berwirausaha
            PieEntry(22f, "Belum Bekerja") // 22% alumni belum bekerja
        )

        // Warna slice mengikuti color palette aplikasi (Maroon, Amber, Abu)
        val colors = listOf(
            Color.parseColor("#9F1239"), // brand_primary (maroon) — Bekerja
            Color.parseColor("#F59E0B"), // amber gold — Wirausaha
            Color.parseColor("#9CA3AF")  // abu-abu netral — Belum Bekerja
        )

        // Konfigurasi DataSet
        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            sliceSpace = 3f          // jarak antar slice (dp)
            selectionShift = 6f      // efek "pop" saat slice di-tap
            valueTextSize = 13f
            valueTextColor = Color.WHITE
            valueTypeface = android.graphics.Typeface.DEFAULT_BOLD
        }

        // Buat PieData dengan formatter persentase
        val pieData = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(chart))
        }

        // Konfigurasi tampilan PieChart
        chart.apply {
            data = pieData
            description.isEnabled = false    // sembunyikan deskripsi default
            isDrawHoleEnabled = true          // donut chart
            holeRadius = 42f                  // ukuran lubang tengah
            transparentCircleRadius = 47f
            setHoleColor(Color.TRANSPARENT)  // method, bukan property
            setUsePercentValues(true)         // tampilkan nilai sebagai %
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(11f)
            isRotationEnabled = true          // bisa di-rotate manual

            // Legend (keterangan warna) di bawah chart
            legend.apply {
                isEnabled = true
                textSize = 12f
                textColor = Color.parseColor("#374151") // text_primary
                formSize = 12f
                xEntrySpace = 16f
            }

            // Teks di tengah donut
            centerText = "Alumni\nTracer Study"
            setCenterTextSize(13f)
            setCenterTextColor(Color.parseColor("#9F1239"))
            setCenterTextTypeface(android.graphics.Typeface.DEFAULT_BOLD)

            // Animasi masuk (Easing dari MPAndroidChart)
            animateY(1200, Easing.EaseInOutQuad)

            // Refresh chart
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}