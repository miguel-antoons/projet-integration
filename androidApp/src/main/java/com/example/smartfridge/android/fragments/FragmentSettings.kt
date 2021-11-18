package com.example.smartfridge.android.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfridge.android.*


class FragmentSettings(private val context: MainActivity) : Fragment()  {
    // Function that displays the fragment 'FragmentProduct' on the screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        loadData(view)
        return view

    }

    // load Email and password pre-recorded
    fun loadData(view: View) {

        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        Log.d("testuser", savedUsername.toString())
        val usernameLayout =  view.findViewById<TextView>(R.id.username)

        usernameLayout.setText(savedUsername.toString())
    }


}
