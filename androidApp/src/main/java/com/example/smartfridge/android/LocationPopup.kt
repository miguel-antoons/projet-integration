package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.*
import com.example.smartfridge.android.fragments.FragmentSettings

class LocationPopup(
    context: MainActivity,
    private val fragmentSettings: FragmentSettings
): Dialog(context) {
    private val firstSpinnerElement = "Nouvel Emplacement"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // show the pop-up on screen
        setContentView(R.layout.new_location_popup)

        setupComponents()
        setupSaveButton()
    }

    private fun setupComponents() {
        val locationSpinner = findViewById<Spinner>(R.id.location_popup_spinner)
        val spinnerItems = arrayOf(firstSpinnerElement) + LocationRepository.locationList
        if (locationSpinner != null) {
            val spinnerAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                spinnerItems
            )
            locationSpinner.adapter = spinnerAdapter
        }
    }

    private fun setupSaveButton() {
        findViewById<Button>(R.id.location_popup_save).setOnClickListener {
            val spinnerValue = findViewById<Spinner>(R.id.location_popup_spinner)
                .selectedItemPosition
            val newLocationValue = findViewById<EditText>(R.id.location_popup_new_location_edit)
                .text.toString()

            when {
                newLocationValue.isEmpty() -> {
                    Toast.makeText(
                        context,
                        "Pas de Nouvelle Valeur Spécifiée",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                spinnerValue == 0 -> {
                    LocationRepository.locationList.add(newLocationValue)
                    LocationRepository.postLocations(context)
                    Toast.makeText(
                        context,
                        "Nouvelle Location Enregistrée",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
                else -> {
                    LocationRepository.locationList[spinnerValue - 1] = newLocationValue
                    LocationRepository.postLocations(context)
                    Toast.makeText(
                        context,
                        "Location Modifiée",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        }
    }
}