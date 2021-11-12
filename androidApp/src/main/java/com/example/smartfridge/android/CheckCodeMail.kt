package com.example.smartfridge.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley


class CheckCodeMail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email_reset_password)

        //Recover the email from the previous activity
        val emailFromForgot = intent
            .getStringExtra("Email")

        //Chang textview through email
        val tvt_email = findViewById<TextView>(R.id.textView_email).apply {

            text = emailFromForgot
        }

        //code input

        val code_editext = findViewById<EditText>(R.id.editTextCode)

        val button_check_code = findViewById<Button>(R.id.button_check_code)
        button_check_code.setOnClickListener {

            // get text in EditText email

            val code_text = code_editext.text.toString().trim { it <= ' ' }

            // if edittext is empty
            if (code_text.isEmpty()) {

                Toast.makeText(
                    this@CheckCodeMail,
                    "Entrer le code",
                    Toast.LENGTH_SHORT

                ).show()

            } else {

                check_code(emailFromForgot, code_text)


            }


        }

        //Button resend create a new code and send new email

        val button_resend_new_code = findViewById<Button>(R.id.button_resend_code)
        button_resend_new_code.setOnClickListener {

            new_code_email(emailFromForgot)
        }





    }

    /**
     * Function check code via email on the Database with API /api/users/reset-password/checkcode/<email>/<code> , GET method
     * If the email is found we check code with comparaison, a message is displayed to tell you
     * else the email is not found, a message is displayed to tell you
     */

    private fun check_code(email: String?, code: String?) {

        // api route
        val url = "http://10.0.2.2:5000/api/users/reset-password/checkcode/$email/$code"
        // 10.0.2.2 Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)

        // create a request queue
        val queue = Volley.newRequestQueue(this)

        // A request for retrieving a JSONArray response body at a given URL
        val jsonObjectRequest = JsonArrayRequest(

            // Type method
            Request.Method.GET, url, null,
            { response ->


                if (response.length() == 0) {
                    Toast.makeText(this, "Veuillez remplir le champ de code !", Toast.LENGTH_SHORT)
                        .show()

                } else if (response[0] == "code is false") {

                    Toast.makeText(this, "Le code est mauvais", Toast.LENGTH_SHORT).show()


                } else if (response[0] == "The code is good") {

                    Toast.makeText(this, "Go changement de mot de passe", Toast.LENGTH_SHORT).show()

                } else {

                    setContentView(R.layout.activity_send_email_reset_password)


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

    /**
     * Function create a new code on the Database with API /api/users/reset-password/checkemail and send mail , GET method
     * If the email is found, a message is displayed to tell you
     * else the email is not found, a message is displayed to tell you
     */

    private fun new_code_email(email: String?) {

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

                 if (response[0] == "message: this email exist") {

                    Toast.makeText(this, "Un nouveau code est crée et envoyer", Toast.LENGTH_SHORT).show()


                } else {

                    setContentView(R.layout.activity_send_email_reset_password)


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
