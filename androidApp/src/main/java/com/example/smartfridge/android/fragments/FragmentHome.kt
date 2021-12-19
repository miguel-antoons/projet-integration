package com.example.smartfridge.android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.*
import com.example.smartfridge.android.FridgesRepository.fridgesList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.smartfridge.android.adapter.FridgesAdapter
import com.example.smartfridge.android.adapter.FridgesItemDecoration

class FragmentHome(
    private val context: MainActivity

) : Fragment() {

    // Function that displays the fragment 'fragment_button' on the screen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonHowTo: Button = view.findViewById(R.id.fragment_button)
        val buttonScan: Button = view.findViewById(R.id.scan_button)

        // This little lines of code set an action to the button (onClickListener)
        buttonHowTo.setOnClickListener{
            val intent = Intent(context, HowToConnect::class.java);
            startActivity(intent);
        }

        buttonScan.setOnClickListener{
            val intent = Intent(context, BarcodeReader::class.java);
            startActivity(intent);
        }
    }

    // When Home_Fragment is created, it will call onCreateView -> inject fragment_home on the page
    override fun onCreateView(inflater: LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val adapter = FridgesAdapter(context, fridgesList, R.layout.item_vertical_fridges)
        FridgesRepository.addFridgesAdapter(adapter)

        // load fridgesRepository
        FridgesRepository.getFridges(context)

        // Get the vertical recyclerview and set the adapter on it
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = adapter

        // Add decoration to the section item_vertical_fridges -> we can find this configuration of design in the FridgesItemDecoration file/class
        verticalRecyclerView.addItemDecoration(FridgesItemDecoration())

        // setup the pull to refresh action
        setupPullToRefresh(view)

        return view
    }

    private fun setupPullToRefresh(view: View) {
        val pullToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        pullToRefresh.setOnRefreshListener {
            // get the new information from back-end
            FridgesRepository.getFridges(context, pullToRefresh)
        }
    }
}