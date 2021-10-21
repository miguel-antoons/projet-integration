package com.example.smartfridge.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.ProductModel
import com.example.smartfridge.android.R

class ProductAdapter(
    private val productList: ArrayList<ProductModel>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // contains the different values of each row
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val productName = view.findViewById<TextView>(R.id.product_name)
        val productLoacation = view.findViewById<TextView>(R.id.product_location)
        val productExpirationPeriod = view.findViewById<TextView>(R.id.expiration_period)
    }

    // insert the rows into the recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ViewHolder(view)
    }

    // function assignes the correct values to the correct recyclerview items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.productName.text = currentProduct.name
        holder.productLoacation.text = currentProduct.location
        holder.productExpirationPeriod.text = currentProduct.expirationDate
    }

    // shows the correct number of items in the recyclerview
    override fun getItemCount(): Int {
        return productList.size
    }
}