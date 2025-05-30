package com.example.lokatravel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokatravel.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTourism)

        // Set GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Ambil data dummy tourism
        val tourismDataList = getTourismDummyData()

        // Set adapter
        val adapter = HomeAdapter(tourismDataList)
        recyclerView.adapter = adapter
    }

    private fun getTourismDummyData(): List<HomeViewModel> {
        return listOf(
            HomeViewModel("Jakarta", R.drawable.jakarta),
            HomeViewModel("Bandung", R.drawable.bandung),
            HomeViewModel("Surabaya", R.drawable.surabaya),
            HomeViewModel("Yogyakarta", R.drawable.yogyakarta),
            HomeViewModel("Bali", R.drawable.bali),
            HomeViewModel("Lombok", R.drawable.lombok),
            HomeViewModel("Medan", R.drawable.medan),
            HomeViewModel("Makassar", R.drawable.makassar),
            HomeViewModel("Malang", R.drawable.malang),
            HomeViewModel("Semarang", R.drawable.semarang),
            HomeViewModel("Palembang", R.drawable.palembang),
            HomeViewModel("Banjarmasin", R.drawable.banjarmasin)
        )
    }
}