package com.example.smartfridge.android

import android.content.Context
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.util.HashMap

object LocationRepository {
    // contains all the locations of the current user
    var locationList = arrayListOf<String>()
    private const val serverUrl = "https://smartfridge.online/api/locations"

    /**
     * Function call an api that will return all the locations of the current user
     */
    fun getLocations(context: Context) {
        locationList.clear()
        val queue = Volley.newRequestQueue(context)

        // api call is here, get all the locations and store them in the 'locationList' array
        val stringRequest = object: StringRequest(
            Method.GET, serverUrl,
            { response ->
                val jsonArray = JSONTokener(response).nextValue() as JSONArray
                val receivedLocations = jsonArray.getJSONObject(0).getString("Locations")
                val locationArray = JSONTokener(receivedLocations).nextValue() as JSONArray

                // fill the array with the received data
                for (i in 0 until locationArray.length()) {
                    locationList.add(locationArray.getString(i))
                }

                println(locationList)

            }, { error -> error.printStackTrace() }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer ${loadToken(context)}"
                return headers }
        }

        queue.add(stringRequest)
    }

    /**
     * Posts the modified locations array to back-end
     */
    fun postLocations(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val locationData = JSONObject()

        try {
            // put the new locations into the data to send
            locationData.put("Locations", JSONArray(locationList))
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        // call the api and print the response
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.PUT, serverUrl, locationData,
            { response ->
                println(response)

                // call the get api here in order to make sure it is called after the new
                // location was added
                getLocations(context)
            }, { error -> error.printStackTrace() }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer ${loadToken(context)}"
                return headers }
        }
        queue.add(jsonObjectRequest)
    }

    // load Email and password pre-recorded
    private fun loadToken(context : Context) : String? {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", null)
    }
}