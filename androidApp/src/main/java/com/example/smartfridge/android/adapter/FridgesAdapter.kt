package com.example.smartfridge.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.FridgesModel
import com.example.smartfridge.android.MainActivity
import com.example.smartfridge.android.R

// This class / adapter will give a original template / setting for recyclerview (vertical or horizontal)
// The private val layoutId : Int is there for
class FridgesAdapter(
    private val context : MainActivity,
    private val fridgesList : List<FridgesModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<FridgesAdapter.ViewHolder>(){

    // Box to tidy all the components to control
    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view) {
        // Get the card view that we want to inject
        val component_card = view.findViewById<CardView>(R.id.card_view)
        val fridgeName = view.findViewById<TextView>(R.id.title_card)
        val fridgeTemperature = view.findViewById<TextView>(R.id.temperature_number)
        val fridgeHumidity = view.findViewById<TextView>(R.id.hygrometry_number)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get fridges information
        val currentFridge = fridgesList[position]

        // update name, temperature, humidity of current fridge
        holder.fridgeName.text = currentFridge.name
        holder.fridgeHumidity.text = currentFridge.humidity.toString() + " %"
        holder.fridgeTemperature.text = currentFridge.temperature.toString() + " °"

    }

    // How many card views we want to inject into the page ?
    override fun getItemCount(): Int = fridgesList.size
}