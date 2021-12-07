package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.*
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

        // setup buttons and different textview
        setupComponents()
        setupSaveButton()
        setupDeleteButton()
        setupCloseButton()
    }

    // close the pop-up when clicking on the close button
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_popup).setOnClickListener {
            dismiss()
        }
    }

    /**
     * Function sets the correct values in the layout's textview ans sets the function that
     * launches on click of the several buttons.
     */
    private fun setupComponents() {
        // retrieve different layout elements of the pop-up
        val raspberryID = findViewById<TextView>(R.id.raspberry_popup_id_default)
        val locationSpinner = findViewById<Spinner>(R.id.raspberry_popup_location_edit)
        val spinnerItems = arrayOf("Nouvel Emplacement") + LocationRepository.locationList

        // set raspberry id
        raspberryID.text = newRaspberries[0].id

        // set values to select inside the location spinner
        if (locationSpinner != null) {
            val spinnerAdapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                spinnerItems
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            locationSpinner.adapter = spinnerAdapter
        }
    }

    /**
     * Calls the delete api that deletes an api. It then closes the pop-up and shows a message
     */
    private fun setupDeleteButton() {
        findViewById<Button>(R.id.raspberry_popup_discard).setOnClickListener {
            fragmentSettings.deleteNewRaspberry(newRaspberries[0])
            Toast.makeText(context, "Nouveau Scanneur Suprimmé", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    /**
     * Function verifies the values entered for a new raspberries and calls the function that will
     * update that same raspberry if the values entered are valid
     */
    private fun setupSaveButton() {
        findViewById<Button>(R.id.raspberry_popup_save).setOnClickListener {
            // get different values from the layout
            val spinnerValue = findViewById<Spinner>(R.id.raspberry_popup_location_edit)
                .selectedItemPosition
            val newLocation = findViewById<EditText>(R.id.raspberry_popup_new_location_edit)
                .text
                .toString()

            // if new location was selected but no new location was given
            if (newLocation.isEmpty() && spinnerValue == 0) {
                // show an error message
                Toast.makeText(context, "Pas de location", Toast.LENGTH_SHORT).show()
            }
            // if new location was selected
            else if (spinnerValue == 0) {
                // modify the raspberry's info and post it
                newRaspberries[0].status = "ready"
                newRaspberries[0].location = newLocation
                fragmentSettings.modifyNewRaspberry(newRaspberries[0])
                Toast.makeText(context, "Nouveau Scanneur Ajouté", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            // if an already existing location was selected
            else {
                // modify the raspberry's info and post it
                newRaspberries[0].status = "ready"
                newRaspberries[0].location = LocationRepository.locationList[spinnerValue - 1]
                fragmentSettings.modifyNewRaspberry(newRaspberries[0])
                Toast.makeText(context, "Nouveau Scanneur Ajouté", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}