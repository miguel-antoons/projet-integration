package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.smartfridge.android.fragments.FragmentSettings

class RaspberryPopup(
    context: MainActivity,
    private val newRaspberries: ArrayList<Raspberry>,
    private val fragmentSettings: FragmentSettings
): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // show the pop-up on screen
        setContentView(R.layout.raspberry_popup)

        setupComponents()
        setupSaveButton()
    }

    private fun setupComponents() {
        val raspberryID = findViewById<TextView>(R.id.raspberry_popup_id_default)
        raspberryID.text = newRaspberries[0].id
    }

    private fun setupDeleteButton() {

    }

    private fun setupSaveButton() {
        findViewById<Button>(R.id.raspberry_popup_save).setOnClickListener {
            val newLocation = findViewById<EditText>(R.id.raspberry_popup_new_location_edit)
                .text
                .toString()

            if (newLocation.isEmpty()) {
                Toast.makeText(context, "Pas de location", Toast.LENGTH_SHORT).show()
            }
            else {
                newRaspberries[0].status = "ready"
                newRaspberries[0].location = newLocation
                fragmentSettings.modifyNewRaspberry(newRaspberries[0])
                Toast.makeText(context, "Nouveau Scanneur Ajouté", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}