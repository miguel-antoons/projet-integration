package com.example.smartfridge.android

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.*

class LocationPopup(
    context: MainActivity
): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // show the pop-up on screen
        setContentView(R.layout.new_location_popup)

        // setup different elements of the page
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
     * Setup different static elements of the pop-up
     */
    private fun setupComponents() {
        val locationSpinner = findViewById<Spinner>(R.id.location_popup_spinner)

        // define the items that will be shown on spinner click
        val spinnerItems = arrayOf("Nouvel Emplacement") + LocationRepository.locationList

        // fill the spinner with the values of spinnerItems
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
     * Sets the action to perform when saving the new location. It verifies if the data entered
     * is correct. If that's the case, it modifies the location array and calls an api to
     * update the data in back-end
     */
    private fun setupSaveButton() {
        findViewById<Button>(R.id.location_popup_save).setOnClickListener {
            // get selected spinner item index
            val spinnerValue = findViewById<Spinner>(R.id.location_popup_spinner)
                .selectedItemPosition
            val newLocationValue = findViewById<EditText>(R.id.location_popup_new_location_edit)
                .text.toString()

            when {
                // if the input is empty show an error message
                newLocationValue.isEmpty() -> {
                    Toast.makeText(
                        context,
                        "Pas de Nouvelle Valeur Spécifiée",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // if the user wants to add a new location
                spinnerValue == 0 -> {
                    // add the new location and call the api to modify the data in back-end
                    LocationRepository.locationList.add(newLocationValue)
                    LocationRepository.postLocations(context)
                    Toast.makeText(
                        context,
                        "Nouvelle Location Enregistrée",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
                // if the user wants to modify an existing location
                else -> {
                    // modify that location locally, then call the api to modify in back-end
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

    /**
     * Function sets the actions to perform when deleting a location. It verifies if the data
     * entered is correct. If that's the case, it deletes the location locally and calls the api
     * to update the back-end locations list.
     */
    private fun setupDeleteButton() {
        findViewById<Button>(R.id.location_popup_discard).setOnClickListener {
            // get selected item index
            val spinnerValue = findViewById<Spinner>(R.id.location_popup_spinner)
                .selectedItemPosition

            when {
                // if there is only one location left
                LocationRepository.locationList.size == 1 -> {
                    // show an error message
                    Toast.makeText(
                        context,
                        "Au Moins 1 Emplacement doit exister",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // if there is no location selected
                spinnerValue == 0 -> {
                    // show an error message
                    Toast.makeText(
                        context,
                        "Aucun Emplacement Sélectionné",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // if everything is ok
                else -> {
                    // remove the item locally, then call the DELETE api to update back-end
                    LocationRepository.locationList.removeAt(spinnerValue - 1)
                    LocationRepository.postLocations(context)
                    Toast.makeText(
                        context,
                        "Emplacement Suprimmé",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        }
    }
}