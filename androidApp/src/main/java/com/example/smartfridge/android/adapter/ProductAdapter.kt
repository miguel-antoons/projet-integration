package com.example.smartfridge.android.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.MainActivity
import com.example.smartfridge.android.ProductModel
import com.example.smartfridge.android.ProductPopup
import com.example.smartfridge.android.R
import kotlin.collections.ArrayList

class ProductAdapter(
    private val productList: ArrayList<ProductModel>,
    val context: MainActivity
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // contains the different values of each row
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val productItem = view
        val productName: TextView = view.findViewById(R.id.product_name)
        val productLocation: TextView = view.findViewById(R.id.product_location)
        val productExpirationPeriod: TextView = view.findViewById(R.id.expiration_period)
        val productStatus: ImageView = view.findViewById(R.id.product_status)
    }

    // insert the rows into the recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ViewHolder(view)
    }

    // function assigns the correct values to the correct recyclerview items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]

        // sets item color to light green if the position is uneven
        if ((position % 2) == 1) {
            holder.productItem.setBackgroundColor(Color.parseColor("#CBF4DA"))
        }

        // create an interaction on product row click
        holder.productItem.setOnClickListener{
            // show product pop-up on click (cf. '../ProductPopup.kt')
            ProductPopup(this, currentProduct, position).show()
        }

        // set product icon color
        holder.productStatus.setColorFilter(Color.parseColor(currentProduct.productColor))

        // set different textfield of each product
        holder.productName.text = currentProduct.name
        holder.productLocation.text = currentProduct.location
        holder.productExpirationPeriod.text = currentProduct.expirationPeriod
    }

    // shows the correct number of items in the recyclerview
    override fun getItemCount(): Int {
        return productList.size
    }
}
