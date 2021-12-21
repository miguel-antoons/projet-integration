package com.example.smartfridge.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class CheckAndSendEmail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_and_send_mail)

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
                    this@CheckAndSendEmail,
                    "Entrer une email",
                    Toast.LENGTH_SHORT

                ).show()

            } else {

                check_email_get(email_text)

            }
        }
        //button go back LOGIN PAGE
        val button_return = findViewById<Button>(R.id.bouton_retour)
        button_return.setOnClickListener {
            val i = Intent(this, Login::class.java)



            startActivity(i)

        }


    }

    /**
     * Function check email on the Database with API /api/users/reset-password , GET method
     * If the email is found, a message is displayed to tell you
     * else the email is not found, a message is displayed to tell you
     */

    private fun check_email_get(email: String?) {

        val putUrl = "https://smartfridge.online/api/users/reset-password/checkemail"
        val requestQueue = Volley.newRequestQueue(this)

        val putData = JSONObject()
        try {

            putData.put("Email", email)


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, putUrl, putData,


            { response ->
                // RETURN API
                println(response.getString("message"))

                if (response.getString("message") == "message: this email does not exist") {

                    Toast.makeText(this, "Email n'existe pas", Toast.LENGTH_SHORT).show()


                } else if (response.getString("message") == "message: this email exist") {

                    Toast.makeText(this, "Changement de page", Toast.LENGTH_SHORT).show()
                    val email_champ_full = findViewById<EditText>(R.id.et_email).text.toString()

                    val i = Intent(this, CheckCodeMail::class.java).apply {
                        putExtra("Email", email_champ_full)
                    }
                    Toast.makeText(this, "Voici l'email : $email_champ_full", Toast.LENGTH_SHORT).show()


                    startActivity(i)


                } else {

                    setContentView(R.layout.activity_check_and_send_mail)


                }



            }


        ) { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


    }
}



