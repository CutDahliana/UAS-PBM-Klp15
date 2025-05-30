package com.example.lokatravel.ui.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokatravel.R

class HomeAdapter(private val dataList: List<HomeViewModel>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewTourism: ImageView = itemView.findViewById(R.id.imageViewTourism)
        private val textViewTourismName: TextView = itemView.findViewById(R.id.textViewTourismName)

        fun bind(data: HomeViewModel) {
            try {
                imageViewTourism.setImageResource(data.imageResourceId)
            } catch (e: Exception) {
                imageViewTourism.setImageResource(R.drawable.contoh)
            }

            textViewTourismName.text = data.name

            itemView.setOnClickListener {
                val context = itemView.context
                val url = when (data.name) {
                    "Jakarta" -> "https://www.tripadvisor.co.id/Attractions-g294229-Activities-Jakarta_Java.html"
                    "Bandung" -> "https://www.tripadvisor.co.id/Attractions-g297704-Activities-Bandung_West_Java_Java.html"
                    "Surabaya" -> "https://www.tripadvisor.co.id/Attractions-g297715-Activities-Surabaya_East_Java_Java.html"
                    "Yogyakarta" -> "https://www.tripadvisor.co.id/Attractions-g294230-Activities-Yogyakarta_Region_Java.html"
                    "Bali" -> "https://www.tripadvisor.co.id/Attractions-g294226-Activities-Bali.html"
                    "Lombok" -> "https://www.tripadvisor.co.id/Attractions-g297733-Activities-c57-Lombok_West_Nusa_Tenggara.html"
                    "Medan" -> "https://www.tripadvisor.co.id/Attractions-g297725-Activities-Medan_North_Sumatra_Sumatra.html"
                    "Makassar" -> "https://www.tripadvisor.co.id/Attractions-g297720-Activities-Makassar_South_Sulawesi_Sulawesi.html"
                    "Malang" -> "https://www.tripadvisor.co.id/Attractions-g297710-Activities-Malang_East_Java_Java.html"
                    "Semarang" -> "https://www.tripadvisor.co.id/Attractions-g297712-Activities-Semarang_Central_Java_Java.html"
                    "Palembang" -> "https://www.tripadvisor.co.id/Attractions-g608507-Activities-Palembang_South_Sumatra_Sumatra.html"
                    "Banjarmasin" -> "https://www.tripadvisor.co.id/Attractions-g680020-Activities-Banjarmasin_South_Kalimantan_Kalimantan.html"
                    else -> "https://www.tripadvisor.co.id/" // fallback
                }

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }
    }
}
