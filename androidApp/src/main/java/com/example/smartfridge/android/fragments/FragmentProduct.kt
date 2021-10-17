package com.example.smartfridge.android.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.MainActivity
import com.example.smartfridge.android.R
import com.example.smartfridge.android.adapter.ProductAdapter
import com.example.smartfridge.android.FormsAddAliments
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FragmentProduct : Fragment() {

    // function shows the fragemnt 'FragmentProduct' on screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_product, container, false)

        val productPageList = view.findViewById<RecyclerView>(R.id.product_page_list)
        productPageList.adapter = ProductAdapter()
        
        // add fragment here
        val bt = view.findViewById<FloatingActionButton>(R.id.addingBtn)

        bt.setOnClickListener {
            activity?.let{
                val intent = Intent (it, FormsAddAliments::class.java)
                it.startActivity(intent)
            }

        }
        return view
    }