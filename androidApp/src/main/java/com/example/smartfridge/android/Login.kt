package com.example.smartfridge.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.VerifyEmailPassword.validateForm
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import com.example.smartfridge.android.Hashing.verifyPasswordHash

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        val emailLayout = findViewById<EditText>(R.id.editTextUser)
        val passwordLayout = findViewById<EditText>(R.id.editTextPassword)
        val signInButton = findViewById<Button>(R.id.buttonLogin)
        val signUpButton = findViewById<Button>(R.id.buttonRegister)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val rememberMe = findViewById<CheckBox>(R.id.rememberMe)

        // pre-load email and password if the rememberMe check box is checked
        loadData()

        // onclick longin button
        signInButton.setOnClickListener{
            if(!validateForm(emailLayout.text.toString(), passwordLayout.text.toString())) {
                Toast.makeText(this, "Email ou mot de passe incorrect !", Toast.LENGTH_SHORT).show()
            }
            else {
                verifyClient(emailLayout.text.toString(), passwordLayout.text.toString())
            }
        }

        // onclick signup button
        signUpButton.setOnClickListener{
            // creation de notre intent
            val monIntent : Intent =  Intent(this,SignUp::class.java)
            // start MainActivity
            startActivity(monIntent)
            Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()
        }

        // onclick forgot password button
        forgotPassword.setOnClickListener{
            // creation de notre intent
            val monIntent : Intent =  Intent(this,CheckAndSendEmail::class.java)
            // start MainActivity
            startActivity(monIntent)
            Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()
        }

    }

    // checks if the email and password are correct in the database
    private fun verifyClient(email : String?, password : String) {
        val url = "http://10.0.2.2:5000/api/login/$email"
        // create a request queue
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->

                // Email verification
                if(response.length() === 0) {
                    Toast.makeText(this, "Email incorrect !", Toast.LENGTH_SHORT).show()
                }

                // Password verification
                else {
                    if(verifyPasswordHash(password, response.getJSONObject(0).getString("Password"))) {

                        // save email and password, username locally
                        val username = response.getJSONObject(0).getString("Username")
                        saveData(email, password, username)

                        // creation de notre intent
                        val monIntent : Intent =  Intent(this,MainActivity::class.java)
                        // start MainActivity
                        startActivity(monIntent)
                    }
                    else {
                        Toast.makeText(this, "Mot de passe incorrect !", Toast.LENGTH_SHORT).show()
                    }
                }

                Log.d("MainActivity", "response: $response")
            }, {
                    error ->
                Log.d("TAGTest", "error: ${error.message}")
                Log.d("MainActivity", "Api call failed")

            }
        )
        queue.add(jsonObjectRequest)
    }

    // save email and password locally
    private fun saveData(email: String?, password: String?, username: String?) {
        val rememberMe = findViewById<CheckBox>(R.id.rememberMe)
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(rememberMe.isChecked){
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("EMAIL", email)
                putString("PASSWORD", password)
                putString("USERNAME", username)
                putBoolean("check", rememberMe.isChecked)
            }.apply()
        }
        else{
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("EMAIL", "email")
                putString("PASSWORD", "password")
                putString("USERNAME", username)
                putBoolean("check", false)
            }.apply()
        }

    }

    // load Email and password pre-recorded
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("EMAIL", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)
        val savedBoolean = sharedPreferences.getBoolean("check", false)

        val emailLayout = findViewById<EditText>(R.id.editTextUser)
        val passwordLayout = findViewById<EditText>(R.id.editTextPassword)
        val rememberMe = findViewById<CheckBox>(R.id.rememberMe)

        Log.d("sharedEmail", savedEmail.toString())

        if(savedBoolean) {
            emailLayout.setText(savedEmail)
            passwordLayout.setText(savedPassword)
            rememberMe.isChecked = savedBoolean
        }
    }
}