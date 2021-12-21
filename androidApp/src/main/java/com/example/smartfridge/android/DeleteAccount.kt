package com.example.smartfridge.android

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.HashMap


class DeleteAccount(
    context: MainActivity
): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // show the pop-up on screen
        setContentView(R.layout.delete_account_popup)

        // setup different elements of the page
        setupCloseButton()
        setupDeleteButton()

    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_popup_account).setOnClickListener {
            dismiss()
        }
    }

    private fun setupDeleteButton() {
        findViewById<Button>(R.id.delte_account_popup_discard).setOnClickListener{
            // API DELETE
            val postUrl = "https://smartfridge.online/api/account"
            val requestQueue = Volley.newRequestQueue(context)

            val jsonObjectRequest = object: StringRequest(
                Method.DELETE, postUrl,
                { response ->
                    println(response)

                }, { error -> error.printStackTrace() }
            ) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["Authorization"] = "Bearer  ${ProductRepository.loadToken(context)}"
                    return headers }
            }

            requestQueue.add(jsonObjectRequest)


            val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("EMAIL", "Email")
                putString("PASSWORD", "Password")
                putString("USERNAME", "Username")
            }.apply()
            val i = Intent(context, Login::class.java)
            context.startActivity(i)
        }
    }
}