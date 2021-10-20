package com.example.smartfridge.android

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.smartfridge.android.ProductRepository.Singleton.productList
import java.util.*

class FormsAddAliments(
) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val categorie = alimentCategorie.getSelectedItem().toString()
            val store = alimentStore.getSelectedItem().toString()


            // adding the new product to the product array in the 'ProductRepository' class
            productList.add(ProductModel(
                names,
                Integer.parseInt(quantite),
                date,
                categorie,
                store
            ))

            Toast.makeText(this ,"Ajout de l'aliment effectué :)" + names + " " + date+ " " + quantite+ " " +categorie + " " +store, Toast.LENGTH_LONG).show();


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


        // Configuration Date Button



        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                textView.setText("" + dayOfMonth + " / " + month + " / " + year)
            }, year, month, day)
            dpd.show()

        }






    }
}