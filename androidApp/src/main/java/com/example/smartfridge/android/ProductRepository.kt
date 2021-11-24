package com.example.smartfridge.android

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.adapter.ProductAdapter
import com.example.smartfridge.android.api.NutritionValues
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

// classes purpose is to keep all the products in it
// (it could also be used later to update the list with data from the database)
object ProductRepository {
    // list which contains all the products
    val productList = arrayListOf<ProductModel>()

    var serverUrl = "http://10.0.2.2:5000/api"

    // only purpose of this is notify that the dataset has changed
    private lateinit var productAdapter: ProductAdapter

    /**
     * Function performs all the necessary actions to add a product to the product list.
     * It was written specifically to work with a form to add a product
     * (cf. './FormsAddAliments.kt') but could be used for other purposes.
     */
    fun addProductFromForm(
        productName: String,
        productQuantity: String,
        productExpirationDate: String,
        productCategory: String,
        productLocation: String): Int {
        // get 'Date' object from string date
        val expirationDate = convertToDate(productExpirationDate)
        // get the difference between today and expiration date in Long format
        val dateDifference = getDateDifference(expirationDate)
        // get the expiration period (i.e. '3 days')
        val expirationPeriod = convertDifferenceToString(dateDifference)
        // get the product color
        val productColor = getProductColor(dateDifference)

        // add the new product to the product list
        productList.add(ProductModel(
            name = productName,
            quantity = productQuantity,
            expirationDate = productExpirationDate,
            expirationPeriod = expirationPeriod,
            category = productCategory,
            location = productLocation,
            productColor = productColor
        ))

        // notify that a new product was added
        // this will update the FragmentProduct page
        productAdapter.notifyItemInserted(productList.lastIndex)

        return productList.lastIndex
    }

    /**
     * Function performs all the necessary actions to update a product in the database
     * First step post request with new data in form and send product id in url
     * Second step delete product in db that match with id
     * Final step add new product with data
     */
    fun modifyProduct(
        context: Context,
        productPosition: Int,
        Utilisateur: String,
        Nom: String,
        Marque: String,
        Quantite: String,
        Ingredients: List<String>,
        Date: String,
        Valeurs: NutritionValues,
        Poids: String,
        Lieu: String,
        Category: String,
        productId: String
    ) {
        val url = "$serverUrl/modifyFood/$productId"
        val requestQueue = Volley.newRequestQueue(context)
        val postData = JSONObject()
        try {
            postData.put("Utilisateur", Utilisateur)
            postData.put("Nom", Nom)
            postData.put("Marque", Marque)
            postData.put("Quantite", Quantite)
            postData.put("Ingredients", Ingredients.joinToString())
            postData.put("Date", Date)
            postData.put("Valeurs", Valeurs)
            postData.put("Poids", Poids)
            postData.put("Lieu", Lieu)
            postData.put("Categorie", Category)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, postData,
            { response ->
                println(response)

                // call the get api here in order to make sure it is called after the new
                // product was added
                getFoodFromMongo(context)
            }
        ) { error -> error.printStackTrace() }
        requestQueue.add(jsonObjectRequest)

        // notify that an item was changed inside the list
        // this will update the FragmentProduct page
        productAdapter.notifyItemChanged(productPosition)
    }

    // deletes an element from productList at a given index (productPosition)
    fun deleteProduct(
        productPosition: Int,
        context: Context,
        Utilisateur: String,
        Nom: String,
        Marque: String,
        Quantite: String,
        Ingredients: List<String>,
        Date: String,
        Valeurs: NutritionValues,
        Poids: String,
        Lieu: String,
        Category: String) {
        productList.removeAt(productPosition)
        // API DELETE
        val postUrl = "$serverUrl/removeFood"
        val requestQueue = Volley.newRequestQueue(context)

        val deleteData = JSONObject()
        try {
            deleteData.put("Utilisateur", Utilisateur)
            deleteData.put("Nom", Nom)
            deleteData.put("Marque", Marque)
            deleteData.put("Quantite", Quantite)
            deleteData.put("Ingredients", Ingredients.joinToString())
            deleteData.put("Date", Date)
            deleteData.put("Valeurs", Valeurs)
            deleteData.put("Poids", Poids)
            deleteData.put("Lieu", Lieu)
            deleteData.put("Categorie", Category)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, deleteData,
            { response ->
                println(response)
            }
        ) { error -> error.printStackTrace() }
        requestQueue.add(jsonObjectRequest)

        // notify that an item was removed
        // this will update the FragmentProduct page
        productAdapter.notifyItemRemoved(productPosition)

        // call the get api here in order to make sure it is called after the new
        // product was added
         getFoodFromMongo(context)
    }


    fun addProductAdapter(adapter: ProductAdapter) {
        // add an adapter
        // WARNING : for the app to work correctly AN ADAPTER MUST ALWAYS BE ADDED
        // without the adapter, the Fragment won't update its contents
        productAdapter = adapter
    }


    /**
     * Function converts a string date with the following format : 'dd / M / yyyy'
     * to a Kotlin Date object.
     * For this function to work correctly, the string date format MUST be respected.
     */
    private fun convertToDate(stringDate: String): Date {
        // convert expiration date to 'LocalDate' type
        val expirationLocalDate = LocalDate.parse(
            stringDate,
            DateTimeFormatter.ofPattern("d/M/yyyy")
        )

        // converting expiration date to 'Date' type and returning it
        return Date
            .from(expirationLocalDate
                .atStartOfDay()
                .atZone(
                    ZoneId
                        .systemDefault()
                )
                .toInstant())
    }

    /**
     * Function calculates the difference between a 'Date' object and the current date.
     * It then returns the difference in days and is precise to the millisecond.
     */
    private fun getDateDifference(expirationDate: Date): Long {
        // get current date date in 'Long' format
        // val currentDate: LocalDateTime = LocalDateTime.now()
        val currentDate: Long = Date().time
        val longExpirationDate: Long = expirationDate.time

        // get difference in days between the current date and the expiration date
        // return the result
        return ((longExpirationDate - currentDate) / (24 * 3600 * 1000))
        //return ChronoUnit.DAYS.between(expirationDate, currentDate)
    }

    /**
     * Function returns a string phrase with the number of days/months/years a long number
     * represents.
     * return examples : '2 ans', '9 mois', '1 jour', ...
     */
    private fun convertDifferenceToString(dateDifference: Long): String {
        when {
            // check if the period is greater than a year
            dateDifference >= 365 -> {
                // calculate the number of years
                val yearDifference: Int = (dateDifference / 365).toInt()
                return (
                        // return the phrase in singular/plural form
                        if (yearDifference <= 1) {
                            "$yearDifference an"
                        }
                        else {
                            "$yearDifference ans"
                        })
            }
            // check if the period is greater than a month
            dateDifference >= 30 -> {
                // calculate the number of months
                val monthDifference: Int = (dateDifference / 30).toInt()
                return "$monthDifference mois"
            }
            // check eventually if the period is greater than a day
            dateDifference > 0 -> {
                return (
                        // return the phrase in singular/plural form
                        if (dateDifference <= 1) {
                            "${kotlin.math.ceil(dateDifference.toDouble()).toInt()} jour"
                        }
                        else {
                            "${kotlin.math.ceil(dateDifference.toDouble()).toInt()} jours"
                        })
            }
            else -> {
                // if the difference is negative, just return '0 days'
                return "0 jours"
            }
        }
    }

    /**
     * Function returns a color according ot the number of days that 'dateDifference' represents.
     * The colors returned by this function were chosen following the maquette at the following
     * link : https://github.com/miguel-antoons/projet-integration/wiki/Maquette
     */
    private fun getProductColor(dateDifference: Long): String {
        return when {
            dateDifference <= 0 -> "#000000"
            dateDifference <= 1 -> "#FF0000"
            dateDifference <= 2 -> "#FF9700"
            else -> "#00A00F"
        }
    }

    /**
     * Function called in order to get all the products of the test user 999 in the database (cf: food.py) and sendFoodToServer().
     * We use adapter in order to notify the product list changed.
     */
    fun getFoodFromMongo(
        context: Context,
    ) {
        val productListLength = productList.size
        productList.clear()

        val url = "$serverUrl/getFood"

        // notify the adapter that everything was removed
        productAdapter.notifyItemRangeRemoved(0, productListLength)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                /*
                https://johncodeos.com/how-to-parse-json-in-android-using-kotlin/
                */
                val jsonArray = JSONTokener(response).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    val expirationDate = convertToDate(jsonArray.getJSONObject(i).getString("Date"))
                    val dateDifference = getDateDifference(expirationDate)
                    val expirationPeriod = convertDifferenceToString(dateDifference)
                    productList.add(
                        ProductModel(
                            id = jsonArray.getJSONObject(i).getString("_id"),
                            name = jsonArray.getJSONObject(i).getString("Nom"),
                            quantity = jsonArray.getJSONObject(i).getString("Quantite"),
                            expirationDate = jsonArray.getJSONObject(i).getString("Date"),
                            expirationPeriod = expirationPeriod,
                            category = jsonArray.getJSONObject(i).getString("Categorie"),
                            brand = jsonArray.getJSONObject(i).getString("Marque"),
                            user = jsonArray.getJSONObject(i).getString("Utilisateur"),
                            location = jsonArray.getJSONObject(i).getString("Lieu"),
                            weight = jsonArray.getJSONObject(i).getString("Poids"),
                            ingredients = listOf(jsonArray.getJSONObject(i).getString("Ingredients")),
                            nutritiveValues = NutritionValues(jsonArray.getJSONObject(i).getString("Valeurs")),
                            productColor = getProductColor(dateDifference)
                        )
                    )

                    // notify the adapter that new elements were added to the list
                     productAdapter.notifyItemRangeInserted(0, jsonArray.length())
                    // productAdapter.notifyDataSetChanged()
                }
                Log.d("GetFood", "SUCCESS")
            },
            { Log.d("GetFood","That didn't work!") }
        )
        queue.add(stringRequest)
    }

    /**
     * Function create API POST request and is called with some parameters
     * @param Valeurs more info check ./api/NutritionValues
     */
    fun sendFoodToServer(
        context: Context,
        Utilisateur: String,
        Nom: String,
        Marque: String,
        Quantite: String,
        Ingredients: List<String>,
        Date: String,
        Valeurs: NutritionValues,
        Poids: String,
        Lieu: String,
        Category: String): String {
        val postUrl = "$serverUrl/addFood"
        val requestQueue = Volley.newRequestQueue(context)

        val postData = JSONObject()
        try {
            postData.put("Utilisateur", Utilisateur)
            postData.put("Nom", Nom)
            postData.put("Marque", Marque)
            postData.put("Quantite", Quantite)
            postData.put("Ingredients", Ingredients.joinToString())
            postData.put("Date", Date)
            postData.put("Valeurs", Valeurs)
            postData.put("Poids", Poids)
            postData.put("Lieu", Lieu)
            postData.put("Categorie", Category)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                println(response)

                // call the get api here in order to make sure it is called after the new
                // product was added
                getFoodFromMongo(context)
            }
        ) { error -> error.printStackTrace() }
        requestQueue.add(jsonObjectRequest)

        return "Produit ajouté"
    }
}
