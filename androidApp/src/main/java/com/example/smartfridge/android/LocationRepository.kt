package com.example.smartfridge.android

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

object LocationRepository {
    var locationList = arrayListOf<String>()
    const val serverUrl = "http://10.0.2.2:5000/api/locations"

    fun getLocations(context: Context) {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        locationList.clear()
        val url = "${serverUrl}/${sharedPreferences.getString("USERNAME", null)}"
        val queue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonArray = JSONTokener(response).nextValue() as JSONArray
                val receivedLocations = jsonArray.getJSONObject(0).getString("Locations")
                val locationArray = JSONTokener(receivedLocations).nextValue() as JSONArray

                for (i in 0 until locationArray.length()) {
                    locationList.add(locationArray.getString(i))
                }

                println(locationList)

            }, { error -> error.printStackTrace() }
        )
        queue.add(stringRequest)
    }

    fun postLocations(context: Context) {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val postUrl = "${serverUrl}/${sharedPreferences.getString("USERNAME", null)}"
        val queue = Volley.newRequestQueue(context)
        val locationData = JSONObject()

        try {
            locationData.put("Locations", JSONArray(locationList))
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, postUrl, locationData,
            { response ->
                println(response)

                // call the get api here in order to make sure it is called after the new
                // product was added
                getLocations(context)
            }, { error -> error.printStackTrace() }
        )
        queue.add(jsonObjectRequest)
    }
}