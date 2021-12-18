package com.example.smartfridge.android


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import androidx.fragment.app.Fragment
import com.example.smartfridge.android.fragments.FragmentHome
import com.example.smartfridge.android.fragments.FragmentProduct
import com.example.smartfridge.android.fragments.FragmentSettings
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavBar:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //apiCall()

        // Navigation Bar
        bottomNavBar = findViewById(R.id.bottomNavBar)
        val homeFragment = FragmentHome(this)
        val productFragment = FragmentProduct(this)
        val settingsFragment = FragmentSettings(this)
        setCurrentFragment(homeFragment)

        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_page-> setCurrentFragment(homeFragment)
                R.id.product_page -> setCurrentFragment(productFragment)
                R.id.settings_page -> setCurrentFragment(settingsFragment)
            }
            true
        }
        LocationRepository.getLocations(this)
    }

    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }


}
