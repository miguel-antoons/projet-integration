package com.example.smartfridge.android.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.adapter.ProductAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartfridge.android.*
import java.text.SimpleDateFormat
import java.util.*


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

        setupSortButtons(view)

        // give the adapter element to the ProductRepository object
        ProductRepository.addProductAdapter(adapter)

        // get all the products from a remote database
        ProductRepository.getFoodFromMongo(context)

        return view
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

    /**
     * Function sets the function to call when the sort buttons are clicked
     */
    private fun setupSortButtons(view: View) {
        // if the 'sort by name' button is clicked, sort the product list by product name
        view.findViewById<View>(R.id.sort_by_name).setOnClickListener {
            // sort the products and store the sorted list in a new variable
            val tempProducts = ProductRepository
                .productList
                .sortedWith(compareBy { it.name.lowercase(Locale.getDefault()) })

            // change the product list contents with the sorted list
            ProductRepository.productList.clear()
            ProductRepository.productList.addAll(tempProducts)

            // notify the adapter of the changes made to the list
            adapter.notifyItemRangeChanged(0, ProductRepository.productList.size)
        }

        // if the 'sort by location' button is clicked, sort the product list by product location
        view.findViewById<View>(R.id.sort_by_location).setOnClickListener {
            // sort the products and store the sorted list in a new variable
            val tempProducts = ProductRepository
                .productList
                .sortedWith(compareBy { it.location.lowercase(Locale.getDefault()) })

            // change the product list contents with the sorted list
            ProductRepository.productList.clear()
            ProductRepository.productList.addAll(tempProducts)

            // notify the adapter of the changes made to the list
            adapter.notifyItemRangeChanged(0, ProductRepository.productList.size)
        }

        // if the 'sort by expiration date' button is clicked, sort the product list by product
        // expiration date
        view.findViewById<View>(R.id.sort_by_expiration_date).setOnClickListener {
            // create a date formatter to change a string date into a Date object
            val dateFormatter = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

            // sort the products by converting the string date to a Date object
            val tempProducts = ProductRepository
                .productList
                .sortedWith(compareBy { dateFormatter.parse(it.expirationDate) })

            // change the product list contents with the sorted list
            ProductRepository.productList.clear()
            ProductRepository.productList.addAll(tempProducts)

            // notify the adapter of the changes made to the list
            adapter.notifyItemRangeChanged(0, ProductRepository.productList.size)
        }
    }

}