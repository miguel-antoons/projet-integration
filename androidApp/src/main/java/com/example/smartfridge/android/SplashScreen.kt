package com.example.smartfridge.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Set animation to res/anim/loading_animation
        val myAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_animation)
        // Apply animation to the FrameLayout
        val circle = findViewById<FrameLayout>(R.id.container_circles)
        circle.startAnimation(myAnimation)

        // Stop Activity after 2000 ms and go to Login activity
        val splashScreenTimeOut = 2000
        val loginIntent = Intent(this, Login::class.java )

        Handler().postDelayed({
          startActivity(loginIntent)
            finish()
        }, splashScreenTimeOut.toLong())

    }
}