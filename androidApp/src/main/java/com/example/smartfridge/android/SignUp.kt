package com.example.smartfridge.android

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import android.widget.*

import com.android.volley.*
import com.example.smartfridge.android.Hashing.passwordHash

/**
 * Class gets all the data from the sign up form and POST them into the MongoDB database.
 * First, there is an initialisation of all the data from the form.
 * Secondly, we work with the data, checking if the user has correctly put data into the field.
 * In conclusion, when data is correctly verified, it can be POST to the database.
 */

class SignUp : AppCompatActivity() {

    // Initialisation of the data for the form
    lateinit var etUsername : EditText
    lateinit var etPassword : EditText
    lateinit var etConfirmPassword : EditText
    lateinit var etEmail : EditText
    lateinit var etHashed : EditText
    private val MIN_PASSWORD_LENGTH = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        // Button of validation / execute the validation of all the fields and call the function performSignUp()
        val buttonReturnUser = findViewById<Button>(R.id.button)
        buttonReturnUser.setOnClickListener {
            etUsername = findViewById(R.id.username)
            etPassword = findViewById(R.id.password)
            etConfirmPassword = findViewById(R.id.confirm_password)
            etEmail = findViewById(R.id.email)

            performSignUp()
        }
    }

    // Checking if the input in form is valid
    private fun validateInput(): Boolean {
        if (etUsername.text.toString() == "") {
            etUsername.setError("Please enter a username !")
            return false
        }
        // Password
        if (etPassword.text.toString() == "") {
            etPassword.setError("Please enter a password !")
        }
        // Same password / verification
        if (etConfirmPassword.text.toString() == "") {
            etConfirmPassword.setError("Please enter a password !")
        }
        // Email checking
        if (etEmail.text.toString() == "") {
            etEmail.setError("Please enter an email !")
        }

        // Checking minimum password length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
            etConfirmPassword.setError("Password does not match !")
            return false
        }

        // Checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please enter a valid email !")
            return false
        }

        return true
    }

    // Determine if the email is valid or not -> return a boolean : true or false
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    // Hook click event
    private fun performSignUp () {
        // If all the fields are correct the go further

        if (validateInput()) {

            // Input is valid, here send data to your server
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()



            /**
             * This example of code check that indeed, when we compare a wrong password with the effective hash code
             * we get from the result -> Password not verified !

            val hashPassword1 = "eliott123"
            val hashPassword2 = "test123"
            val bcryptHashString2 = BCrypt.withDefaults().hashToString(12, hashPassword2.toCharArray())
            // same with 1

            val result2 = BCrypt.verifyer().verify(hashPassword.toCharArray(), bcryptHashString2)
            // same with 1

            if (result2.verified) {
            Toast.makeText(this, "Password verified !", Toast.LENGTH_SHORT).show()
            }
            else {
            Toast.makeText(this, "Password not verified !", Toast.LENGTH_SHORT).show()
            }
             **/

            // println(getRandomString(12))
            // Here you can call your API
            val postUrl = "http://10.0.2.2:5000/api/addUser"
            val requestQueue = Volley.newRequestQueue(this)

            val postData = JSONObject()
            try {
                postData.put("Username", username)
                postData.put("Password", passwordHash(etPassword.text.toString()))
                postData.put("Email", email)

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, postUrl, postData,
                { response -> println(response)}
            ) {error -> error.printStackTrace()}
            requestQueue.add(jsonObjectRequest)
        }
    }
}
