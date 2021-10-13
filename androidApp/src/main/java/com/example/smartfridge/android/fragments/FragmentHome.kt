package com.example.smartfridge.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.R
import com.example.smartfridge.android.adapter.FridgesAdapter

class FragmentHome : Fragment() {

    // When Home_Fragment is created, it will call onCreateView -> inject fragment_home on the page
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // récupérer le recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = FridgesAdapter()

        return view
    }
}