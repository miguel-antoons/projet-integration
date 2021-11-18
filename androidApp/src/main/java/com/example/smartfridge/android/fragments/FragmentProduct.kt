package com.example.smartfridge.android.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.adapter.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.*
import com.example.smartfridge.android.api.NutritionValues
import org.json.JSONArray
import org.json.JSONTokener


class FragmentProduct(private val context: MainActivity) : Fragment() {
    // initiate adapter to be able to use after 'onCreateView' function
    private lateinit var adapter: ProductAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // Function that displays the fragment 'FragmentProduct' on the screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val productPageList = view.findViewById<RecyclerView>(R.id.product_page_list)
        linearLayoutManager = LinearLayoutManager(context)
        productPageList.layoutManager = linearLayoutManager
        // give the adapter to the fragment
        ProductRepository.getFoodFromMongo(context)
        adapter =  ProductAdapter(ProductRepository.productList, context, this)
        productPageList.adapter = adapter
        // Add fragment here
        val bt = view.findViewById<FloatingActionButton>(R.id.addingBtn)

        // this button will open an new activity with a form to add a new product
        bt.setOnClickListener {
            activity?.let {
                val intent = Intent(it, FormsAddAliments::class.java)
                it.startActivity(intent)
            }
        }
        return view
    }

    // when the fragment resumes, the adapter is notified of potential changes to the data
    // this is done to update the list when new data is added
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        adapter.notifyDataSetChanged()
        super.onResume()
    }


    /**
     * Function starts the 'FormsAddAliments' activity and gives the product index as extra
     * to the activity.
     * This function was specifically designed to work with the product pop-up (cf. ./ProductPopup)
     * and to modify a product.
     */
    fun modifyProductForm(productIndex: Int) {
        activity?.let {
            val intent = Intent(it, FormsAddAliments::class.java)
                // pass the product index to the new activity
                .putExtra("productIndex", productIndex)
            // start the activity
            it.startActivity(intent)
        }
    }


}