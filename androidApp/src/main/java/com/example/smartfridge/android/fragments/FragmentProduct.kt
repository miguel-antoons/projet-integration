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
        adapter =  ProductAdapter(ProductRepository.productList, context)
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
    override fun onResume() {
        adapter.notifyDataSetChanged()
        super.onResume()
    }
}