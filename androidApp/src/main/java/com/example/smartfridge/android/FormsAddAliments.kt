package com.example.smartfridge.android

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import java.util.*
import com.example.smartfridge.android.api.NutritionValues


class FormsAddAliments(
) : AppCompatActivity() {
    private var productIndex: Int = -1

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productIndex = intent.getIntExtra("productIndex", -1)
        setContentView(R.layout.activity_forms_add_aliments)

        // Edit Text Name and Quantite Aliment
        val alimentName = findViewById<EditText>(R.id.AlimentName)
        val alimentQuantite = findViewById<EditText>(R.id.AlimentQuantite)

        val returnImage = findViewById<ImageView>(R.id.return_icon)
        ViewCompat.setTranslationZ(returnImage, 10F)

        // Date peeremption Textview and Button
        val mPickTimeBtn = findViewById<Button>(R.id.button_date_select)
        val textView = findViewById<TextView>(R.id.dateTv)

        // Spinner Categorie and Place for Aliment
        val alimentCategorie = findViewById<Spinner>(R.id.categorie_spinner)
        val alimentStore = findViewById<Spinner>(R.id.place_spinner)

        //Button event
        val button_return_product = findViewById<TextView>(R.id.button_return_list_product)
        ViewCompat.setElevation(button_return_product, 1F)
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

            //check if the EditText have values or not
            if (names.isEmpty()) {

                Toast.makeText(applicationContext, " Name Required ", Toast.LENGTH_SHORT).show()
            } else if (quantite.isEmpty()) {

                Toast.makeText(applicationContext, "Quantite Required ", Toast.LENGTH_SHORT).show()
            }
            else if (date.isEmpty()) {

                Toast.makeText(applicationContext, "Date Required ", Toast.LENGTH_SHORT).show()
            }

            else {

                Toast.makeText(applicationContext, "Aliment Ajouté ", Toast.LENGTH_SHORT).show()

                if (productIndex == -1) {

                    val confirmationMessage = ProductRepository.sendFoodToServer(
                        this,
                        loadUsername(this),
                        names,
                        "TODO",
                        quantite,
                        listOf("ingredient1","ingredient2","ingredient3"),
                        date,
                        NutritionValues(),
                        store,
                        categorie,
                        "X",
                        "X"
                    )

                    Toast.makeText(
                        this,
                        confirmationMessage,
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    ProductRepository.modifyProduct(
                        this,
                        productIndex,
                        loadUsername(this),
                        names,
                        "TODO",
                        quantite,
                        date,
                        store,
                        categorie,
                        ProductRepository.productList[productIndex].id
                    )

                    Toast.makeText(this ,
                        "Produit modifié",
                        Toast.LENGTH_LONG
                    ).show()
                }
                finish()
            }
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
                textView.text = "$dayOfMonth/${monthOfYear + 1}/$year"
            }, year, month, day)
            dpd.show()
        }
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
        quantityField.setText(productCopy.quantity)
        dateField.text = productCopy.expirationDate
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

// load Username
fun loadUsername(context: Context) : String {
    val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
    val savedUsername = sharedPreferences.getString("USERNAME", null)

    return savedUsername.toString()
}