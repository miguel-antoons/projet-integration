package com.example.smartfridge.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class CheckCodeMail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email_reset_password)


        //Recover the email from the previous activity
        val emailFromForgot = intent
            .getStringExtra("Email")

        //code input

        val codeEditext = findViewById<EditText>(R.id.editTextCode)

        val buttonCheckCode = findViewById<Button>(R.id.button_continued)
        buttonCheckCode.setOnClickListener {

            // get text in EditText email

            val codeText = codeEditext.text.toString().trim { it <= ' ' }

            // if edittext is empty
            if (codeText.isEmpty()) {

                Toast.makeText(
                    this@CheckCodeMail,
                    "Entrer le code",
                    Toast.LENGTH_SHORT

                ).show()

            } else {

                checkCode(emailFromForgot, codeText)


            }


        }

        //Button resend create a new code and send new email

        val buttonResendNewCode = findViewById<Button>(R.id.button_resend_code)
        buttonResendNewCode.setOnClickListener {

            newCodeEmail(emailFromForgot)
        }

        //BUTTON GO BACK CHECK MAIL PAGE
        val buttonReturn = findViewById<Button>(R.id.bouton_retour)
        buttonReturn.setOnClickListener {
            val i = Intent(this, CheckAndSendEmail::class.java)



            startActivity(i)

        }


    }

    /**
     * Function check code via email on the Database with API /api/users/reset-password/checkcode/<email>/<code> , GET method
     * If the email is found we check code with comparaison, a message is displayed to tell you
     * else the email is not found, a message is displayed to tell you
     */

    private var essaie = 0

    private fun checkCode(email: String?, code: String?) {

        // api route
        val putUrl = "https://smartfridge.online/api/users/reset-password/checkcode"
        // 10.0.2.2 Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)

        // create a request queue
        val queue = Volley.newRequestQueue(this)

        val putData = JSONObject()
        try {

            putData.put("Email", email)
            putData.put("Code", code)


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // A request for retrieving a JSONArray response body at a given URL
        val jsonObjectRequest = JsonObjectRequest(

            // Type method
            Request.Method.PUT, putUrl, putData,

            { response ->


                if (response.getString("message") == "code is false") {


                    essaie += 1
                    Toast.makeText(this, "Le code est mauvais $essaie / 3", Toast.LENGTH_SHORT)
                        .show()

                    if (essaie == 3) {
                        Toast.makeText(
                            this,
                            "Vous avez échoué 3 fois veuillez recommencer ",
                            Toast.LENGTH_SHORT
                        ).show()
                        val i = Intent(this, Login::class.java)


                        startActivity(i)


                    }


                } else if (response.getString("message") == "The code is good") {

                    Toast.makeText(this, "Go changement de mot de passe", Toast.LENGTH_SHORT).show()
                    //Chang textview through email
                    val tvtEmailText = findViewById<TextView>(R.id.textView_email).text


                    val i = Intent(this, UpdatePassword::class.java).apply {
                        putExtra("Email", tvtEmailText)
                        putExtra("Code", code)
                    }

                    startActivity(i)


                } else {

                    setContentView(R.layout.activity_send_email_reset_password)


                }



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

    private fun newCodeEmail(email: String?) {

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

                when {
                    response.getString("message") == "message: this email does not exist" -> {

                        Toast.makeText(this, "Email n'existe pas", Toast.LENGTH_SHORT).show()


                    }
                    response.getString("message") == "message: this email exist" -> {

                        Toast.makeText(this, "Changement de page", Toast.LENGTH_SHORT).show()
                        val emailChampFull = findViewById<EditText>(R.id.et_email).text.toString()

                        val i = Intent(this, CheckCodeMail::class.java).apply {
                            putExtra("Email", emailChampFull)


                        }

                        startActivity(i)


                    }
                    else -> {

                        setContentView(R.layout.activity_check_and_send_mail)


                    }
                }
                Toast.makeText(this, "$response", Toast.LENGTH_SHORT).show()


            }


        ) { error -> error.printStackTrace() }

        requestQueue.add(jsonObjectRequest)


    }


}
