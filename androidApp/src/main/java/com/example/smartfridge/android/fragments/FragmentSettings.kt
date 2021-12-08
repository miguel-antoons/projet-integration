package com.example.smartfridge.android.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener


class FragmentSettings(private val context: MainActivity) : Fragment()  {
    // Initialisation of the data for the QR code
    private lateinit var ivQRcode: ImageView
    private lateinit var etData : String
    private lateinit var raspberryButton: Button
    private val resultingData = arrayListOf<Raspberry>()
    private val serverUrl = "http://10.0.2.2:5000/api/raspberry"

    // Function that displays the fragment 'FragmentProduct' on the screen
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val disconnect: Button = view.findViewById(R.id.disconnect)
        val newLocationButton = view.findViewById<Button>(R.id.new_location)
        raspberryButton = view.findViewById(R.id.new_raspberry)

        // create qrCode
        // qrCode(view)

        val username = view.findViewById<TextView>(R.id.usernameSettings)
        username.text = loadUsername()

        // This little lines of code set an action to the button (onClickListener)
        disconnect.setOnClickListener{
            deleteData()
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }

        // buttons will open a pop-up to modify user locations
        newLocationButton.setOnClickListener {
            LocationPopup(context).show()
        }

        // button opens a pop-up with inputs to add a new raspberry
        raspberryButton.setOnClickListener {
            RaspberryPopup(context, resultingData, this).show()
        }

        getNewRaspberry()

        return view

    }

    // load Username
    private fun loadUsername() : String {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("USERNAME", null)

        return savedUsername.toString()
    }

    // QR code generating
    private fun qrCode (view: View) {
        etData = loadUsername()
        ivQRcode = view.findViewById(R.id.ivQRcode)

        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(etData, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until height) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            ivQRcode.setImageBitmap(bmp)

        } catch (e : WriterException) {
            e.printStackTrace()
        }
    }

    // save email and password locally
    private fun deleteData() {
        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("EMAIL", "Email")
            putString("PASSWORD", "Password")
            putString("USERNAME", "Username")
        }.apply()
    }

    /**
     * Function calls the api to get all new raspberries of the current user. It then stores these
     * raspberries in an array. If there are new raspberries, the button to validate a raspberry
     * will show on screen (cf. 'raspberryButton').
     */
    private fun getNewRaspberry() {
        val url = "${serverUrl}/${loadUsername()}"
        val queue = Volley.newRequestQueue(context)

        // get all the new raspberries
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonData = JSONTokener(response).nextValue() as JSONArray
                resultingData.clear()

                // store them in an array with correct values
                for (i in 0 until jsonData.length()) {
                    resultingData.add(
                        Raspberry(
                            jsonData.getJSONObject(i).getString("_id"),
                            jsonData.getJSONObject(i).getString("user"),
                            jsonData.getJSONObject(i).getString("location"),
                            jsonData.getJSONObject(i).getString("status")
                        )
                    )
                    println("All raspberries collected")
                }

                // if there are new raspberries, show the button to confirm them
                if (jsonData.length() > 0) {
                    raspberryButton.visibility = View.VISIBLE
                }
                // else hide the button since it's not needed
                else {
                    raspberryButton.visibility = View.GONE
                }
            },
            { Log.d("GetRaspberry","didn't work") }
        )
        queue.add(stringRequest)
    }

    /**
     * Function calls the api and provides the modified raspberry to back-end.
     */
    fun modifyNewRaspberry(newRaspberry: Raspberry) {
        val requestQueue = Volley.newRequestQueue(context)
        val newJsonRaspberry = JSONObject()

        // add the validated to the data that will be sent
        try {
            newJsonRaspberry.put("_id", newRaspberry.id)
            newJsonRaspberry.put("user", newRaspberry.user)
            newJsonRaspberry.put("location", newRaspberry.location)
            newJsonRaspberry.put("status", newRaspberry.status)
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }

        // call the PUT api and print its response
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, serverUrl, newJsonRaspberry,
            { response ->
                println(response)

                // call the get api here to make sure it's called after the new raspberry is
                // validated
                getNewRaspberry()
            },
            { error -> error.printStackTrace() }
        )
        requestQueue.add(jsonObjectRequest)
    }

    /**
     * Function calls an api to delete a new raspberry from back-end.
     */
    fun deleteNewRaspberry(newRaspberry: Raspberry) {
        val requestQueue = Volley.newRequestQueue(context)
        val raspberryID = newRaspberry.id

        // create the url with the raspberry id to delete
        val deleteUrl = "$serverUrl/$raspberryID"

        // call the DELETE api and print its response
        val jsonObjectRequest = StringRequest(
            Request.Method.DELETE, deleteUrl,
            { response ->
                println(response)

                // call the get api here to make sure it's called after the raspberry has been
                // deleted
                getNewRaspberry()
            }
        ) { error -> error.printStackTrace() }
        requestQueue.add(jsonObjectRequest)
    }
}