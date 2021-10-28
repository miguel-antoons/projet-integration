package com.example.smartfridge.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Edit Text Email

        val email = findViewById<EditText>(R.id.et_email)

        //Button continue event

        val button_continue = findViewById<Button>(R.id.bt_forget)

        button_continue.setOnClickListener {

            // get text in EditText email

            val email_text = email.text.toString().trim { it <= ' ' }

            // if edittext is empty
            if (email_text.isEmpty()) {

                Toast.makeText(
                    this@ForgotPassword,
                    "Champ vide : Veuillez insérer un adresse émail.",
                    Toast.LENGTH_SHORT

                ).show()

            } else {

                check_email(email_text)


            }
        }


    }

    private fun check_email(email: String?) {

        // api route
        val url = "http://10.0.2.2:5000/api/users/reset-password/$email"

        // create a request queue
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonArrayRequest(

            // Type method
            Request.Method.GET, url, null,
            { response ->


                if (response.length() === 0) {
                    Toast.makeText(this, "Email incorrect !", Toast.LENGTH_SHORT).show()
                } else {

                    setContentView(R.layout.activity_forgot_password)
                }
                Toast.makeText(this, "$response", Toast.LENGTH_SHORT).show()

                Log.d("MainActivity", "response: $response")
            }, { error ->
                Log.d("TAGTest", "error: ${error.message}")
                Log.d("MainActivity", "Api call failed")

            }
        )
        queue.add(jsonObjectRequest)
    }
}