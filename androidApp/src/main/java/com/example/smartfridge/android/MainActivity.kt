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
    private val CHANNEL_ID = "channel_id_01"
    private val notificationId = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //apiCall()

        // Navigation Bar
        bottomNavBar = findViewById(R.id.bottomNavBar)
        val homeFragment = FragmentHome()
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
        createNotificationChannel()

        LocationRepository.getLocations(this)
    }

    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment)
            commit()
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Alertes SmartFridge"
            val descriptionText = "Géré vos paramètres de notifications ici."
            val importance:Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun sendNotification(){
        val intent = Intent(this, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notif_smart)
            .setContentTitle("SmartFridge")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Un ou plusieurs de vos aliments périments dans les 2 jours qui viennent, pensez à jeter" +
                        "                    un coup d'oeil à votre liste !"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }
}
