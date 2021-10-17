package com.example.smartfridge.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.R

class FridgesAdapter(private val layoutId: Int) : RecyclerView.Adapter<FridgesAdapter.ViewHolder>(){

    // Boite pour ranger tous les composants à controler
    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view) {
        val component_card = view.findViewById<CardView>(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 3
}