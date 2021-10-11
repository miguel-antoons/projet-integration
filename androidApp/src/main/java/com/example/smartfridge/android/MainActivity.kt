package com.example.smartfridge.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartfridge.Greeting
import android.widget.TextView
import com.example.smartfridge.android.fragments.Home_Fragment

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inject the fragment into our box (fragment_button)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_button, Home_Fragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
