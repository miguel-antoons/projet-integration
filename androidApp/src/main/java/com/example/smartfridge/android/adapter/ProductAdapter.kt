package com.example.smartfridge.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // not used yet, will be used later on
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val productItem = view.findViewById<RecyclerView>(R.id.product_page_list)
    }

    // insert the rows into the recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ViewHolder(view)
    }

    // not used yet, will be used later on
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    // shows 5 items on the screen
    override fun getItemCount(): Int {
        return 5
    }
}