package com.example.smartfridge.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartfridge.Greeting

import androidx.fragment.app.Fragment
import com.example.smartfridge.android.fragments.FragmentHome
import com.example.smartfridge.android.fragments.FragmentProduct
import com.example.smartfridge.android.fragments.FragmentSettings
import com.google.android.material.bottomnavigation.BottomNavigationView

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavBar:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBar = findViewById(R.id.bottomNavBar)
        val homeFragment = FragmentHome()
        val productFragment = FragmentProduct()
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

    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }
}
