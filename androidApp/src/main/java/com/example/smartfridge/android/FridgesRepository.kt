package com.example.smartfridge.android

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.adapter.FridgesAdapter
import com.example.smartfridge.android.adapter.ProductAdapter
import com.example.smartfridge.android.api.NutritionValues
import org.json.JSONArray
import org.json.JSONTokener
import java.util.HashMap

object FridgesRepository {

    var serverUrl = "http://10.0.2.2:5000/api/environnement"

    // create list with all fridges
    val fridgesList = arrayListOf<FridgesModel>()

    // only purpose of this is notify that the dataset has changed
    private lateinit var fridgesAdaptater: FridgesAdapter


    /**
     * Function called in order to get all the fridges in the database
     * We use adapter in order to notify the product list changed.
     */

    fun getFridges(context: Context){
        val fridgesListLength = fridgesList.size
        fridgesList.clear()

        // notify the adapter that everything was removed
        fridgesAdaptater.notifyItemRangeRemoved(0, fridgesListLength)

        val url = serverUrl
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : JsonArrayRequest(
            Method.GET, url, null,
            { response ->
                /*
                https://johncodeos.com/how-to-parse-json-in-android-using-kotlin/
                */
                for (i in 0 until response.length()) {
                    fridgesList.add(
                        FridgesModel(
                            name = response.getJSONObject(i).getString("location"),
                            temperature = response.getJSONObject(i).getDouble("Temperature"),
                            humidity = response.getJSONObject(i).getDouble("Humidity"),
                            lux = response.getJSONObject(i).getDouble("Light")
                        )
                    )
                }

                // notify the adapter that new elements were added to the list
                fridgesAdaptater.notifyItemRangeInserted(0, fridgesList.size)
                Log.d("GetFridges", "SUCCESS")

            },
            { Log.d("GetFridges","That didn't work!") }
        ){
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer ${loadToken(context)}"
                return headers }
        }
        queue.add(stringRequest)
    }

    // load Email and password pre-recorded
    private fun loadToken(context : Context) : String? {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", null)
    }

    fun addFridgesAdapter(adapter: FridgesAdapter) {
        // add an adapter
        // WARNING : for the app to work correctly AN ADAPTER MUST ALWAYS BE ADDED
        // without the adapter, the Fragment won't update its contents
        fridgesAdaptater = adapter
    }


}