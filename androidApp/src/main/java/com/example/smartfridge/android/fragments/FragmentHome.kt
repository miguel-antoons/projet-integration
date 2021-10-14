package com.example.smartfridge.android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.smartfridge.android.HowToConnect
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.R
import com.example.smartfridge.android.adapter.FridgesAdapter

class FragmentHome : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonHowTo: Button = view.findViewById(R.id.fragment_button)
        buttonHowTo.setOnClickListener{
            val intent = Intent(context, HowToConnect::class.java);
            startActivity(intent);
        }
    }
    // TEST
    // When Home_Fragment is created, it will call onCreateView -> inject fragment_home on the page
    override fun onCreateView(inflater: LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // Get recyclerview vertical
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = FridgesAdapter(R.layout.item_vertical_fridges)

        return view
    }
}