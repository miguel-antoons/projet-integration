package com.example.smartfridge.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.fragments.FragmentHome
import com.example.smartfridge.android.fragments.FragmentProduct
import com.example.smartfridge.android.fragments.FragmentSettings
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavBar:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiCall()

        // Navigation Bar
        bottomNavBar = findViewById(R.id.bottomNavBar)
        val homeFragment = FragmentHome()
        val productFragment = FragmentProduct(this)
        val profileFragment = FragmentSettings()
        setCurrentFragment(homeFragment)

        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page-> setCurrentFragment(homeFragment)
                R.id.product_page -> setCurrentFragment(productFragment)
                R.id.settings_page -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    /**
     *  /!\ This function is still needed ?
     */
    private fun apiCall() {
        val url = "http://10.0.2.2:5000/client"
        // create a request queue
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest  = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val str = response.toString()
                Log.d("MainActivity", "response: $str")
            }, {
                    error ->
                Log.d("TAGTest", "error: ${error.message}")
                Log.d("MainActivity", "Api call failed")

            }
        )

        queue.add(jsonObjectRequest)
    }

    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }
}
