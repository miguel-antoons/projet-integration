package com.example.smartfridge.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

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

                check_email(email_text)

            }
        }


    }

    /**
     * Function check email on the Database with API /api/users/reset-password , GET method
     * If the email is found, a message is displayed to tell you
     * else the email is not found, a message is displayed to tell you
     */

    private fun check_email(email: String?) {

        // api route
        val url = "http://10.0.2.2:5000/api/users/reset-password/checkemail/$email"
        // 10.0.2.2 Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)

        // create a request queue
        val queue = Volley.newRequestQueue(this)

        // A request for retrieving a JSONArray response body at a given URL
        val jsonObjectRequest = JsonArrayRequest(

            // Type method
            Request.Method.GET, url, null,
            { response ->


                if (response.length() == 0) {
                    Toast.makeText(this, "Veuillez remplir le champ email !", Toast.LENGTH_SHORT)
                        .show()

                } else if (response[0] == "message: this email does not exist") {

                    Toast.makeText(this, "Email n'existe pas", Toast.LENGTH_SHORT).show()


                } else if (response[0] == "message: this email exist") {

                    Toast.makeText(this, "Changement de page", Toast.LENGTH_SHORT).show()
                    val email_champ_full = findViewById<EditText>(R.id.et_email).text.toString()

                    val i = Intent(this, CheckCodeMail::class.java).apply {
                        putExtra("Email",email_champ_full)
                    }

                    startActivity(i)




                } else {

                    setContentView(R.layout.activity_check_and_send_mail)


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

