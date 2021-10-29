package com.example.smartfridge.android

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class FormsAddAliments(
) : AppCompatActivity() {
    private var productIndex: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productIndex = intent.getIntExtra("productIndex", -1)
        setContentView(R.layout.activity_forms_add_aliments)

        // Edit Text Name and Quantite Aliment
        val alimentName = findViewById<EditText>(R.id.AlimentName)
        val alimentQuantite = findViewById<EditText>(R.id.AlimentQuantite)

        // Date peeremption Textview and Button
        val mPickTimeBtn = findViewById<Button>(R.id.button_date_select)
        val textView     = findViewById<TextView>(R.id.dateTv)

        // Spinner Categorie and Place for Aliment
        val alimentCategorie = findViewById<Spinner>(R.id.categorie_spinner)
        val alimentStore = findViewById<Spinner>(R.id.place_spinner)

        //Button event
        val button_return_product = findViewById<Button>(R.id.button_return_list_product)
        button_return_product.setOnClickListener {
            // end the activity and return to the previous fragment
            finish()
        }

        val button_add_aliment = findViewById<Button>(R.id.button_add_aliment)
        button_add_aliment.setOnClickListener {

            val names = alimentName.text.toString()
            val quantite = alimentQuantite.text.toString()
            val date = textView.text.toString()
            val categorie = alimentCategorie.selectedItem.toString()
            val store = alimentStore.selectedItem.toString()

            if (productIndex == -1) {
                addProduct(names, Integer.parseInt(quantite), date, categorie, store)
            }
            else {
                modifyProduct(productIndex, names, Integer.parseInt(quantite), date, categorie, store)
            }

            finish()
        }

        // Spinner Categorie change catégorie

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.categorie_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            alimentCategorie.adapter = adapter
        }

        // Spinner Place change Place
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.place_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            alimentStore.adapter = adapter
        }

        // if a productIndex was given
        if (productIndex != -1) {
            // prepare the form to modify a product instead of creating a new product
            prepModifyForm(
                productIndex,
                alimentName,
                alimentQuantite,
                textView,
                alimentCategorie,
                alimentStore,
                button_add_aliment
            )
        }

        // Configuration Date Button
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                textView.text = "$dayOfMonth / ${monthOfYear + 1} / $year"
            }, year, month, day)
            dpd.show()
        }
    }

    /**
     * Function adds a new product to the system trough the ProductRepository.addProductFromForm
     * method (cf./ProductRepository.kt). It then show a confirmation message on screen when the
     * product was added.
     */
    private fun addProduct(
        productName: String,
        productQuantity: Int,
        expirationDate: String,
        productCategory: String,
        productLocation: String
    ) {
        // adding the new product to the product array in the 'ProductRepository' class
        ProductRepository.addProductFromForm(
            productName,
            productQuantity,
            expirationDate,
            productCategory,
            productLocation
        )

        Toast.makeText(this ,
            "Produit ajouté", Toast.LENGTH_LONG).show();
    }

    /**
     * Function modifies a product through the ProductRepository.modifyProduct method
     * (cf./ProductRepository.kt). It then show a confirmation message on screen when the
     * product was modified.
     */
    private fun modifyProduct(
        productIndex: Int,
        productName: String,
        productQuantity: Int,
        expirationDate: String,
        productCategory: String,
        productLocation: String
    ) {
        // call the method to modify the product and give it the new values
        ProductRepository.modifyProduct(
            productIndex,
            productName,
            productQuantity,
            expirationDate,
            productCategory,
            productLocation
        )

        Toast.makeText(this ,
            "Produit modifié", Toast.LENGTH_LONG).show();
    }

    /**
     * Function prepares the form to modify a product instead of creating a new one. It fills all
     * the product fields with the value of the product that the user wants to modify and alters
     * the submit button text.
     */
    private fun prepModifyForm(
        productIndex: Int,
        nameField: EditText,
        quantityField: EditText,
        dateField: TextView,
        categorySpinner: Spinner,
        locationSpinner: Spinner,
        updateButton: Button
    ) {
        // create a copy of the product
        val productCopy = ProductRepository.productList[productIndex]

        // pre-set the different product fields
        nameField.setText(productCopy.name)
        quantityField.setText(productCopy.quantity.toString())
        dateField.text = SimpleDateFormat("dd / M / yyyy", Locale.getDefault())
            .format(productCopy.expirationDate)
        categorySpinner.setSelection(
            resources.getStringArray(R.array.categorie_array).indexOf(productCopy.category)
        )
        locationSpinner.setSelection(
            resources.getStringArray(R.array.place_array).indexOf(productCopy.location)
        )

        // alter the submit button text
        updateButton.text = resources.getText(R.string.btn_update)
    }
}
