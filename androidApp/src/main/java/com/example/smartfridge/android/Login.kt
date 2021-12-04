package com.example.smartfridge.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.smartfridge.android.VerifyEmailPassword.validateForm
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import com.example.smartfridge.android.Hashing.verifyPasswordHash
import com.example.smartfridge.android.Hashing.passwordHash
import org.json.JSONException
import com.android.volley.RequestQueue




class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        val emailLayout = findViewById<EditText>(R.id.editTextUser)
        val passwordLayout = findViewById<EditText>(R.id.editTextPassword)
        val signInButton = findViewById<Button>(R.id.buttonLogin)
        val signUpButton = findViewById<Button>(R.id.buttonRegister)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val showPassword = findViewById<CheckBox>(R.id.showPassword)


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

        // show or hide password
        showPassword.setOnCheckedChangeListener { _, isChecked1 ->
            if (isChecked1) {
                passwordLayout.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else {
                passwordLayout.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordLayout.setSelection(passwordLayout.getText().length)
            }
        }

    }

    // checks if the email and password are correct in the database
    private fun verifyClient(email : String, password : String) {
        val url = "http://10.0.2.2:5000/api/login"
        // create a request queue
        val queue = Volley.newRequestQueue(this)

        val postData = JSONObject()
        try {
            postData.put("Email", email)
            postData.put("Password", password)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, postData,
            { resp ->
                // save email, password, username, token locally
                val username = resp.getString("Username")
                val token = resp.getString("access_token")
                Log.d("Token", token)
                saveData(email, password, username, token)

                // creation de notre intent
                val monIntent : Intent =  Intent(this,MainActivity::class.java)
                // start MainActivity
                startActivity(monIntent)


            }, {
                    error ->
                Toast.makeText(this, "Email ou mot de passe incorrect !", Toast.LENGTH_SHORT).show()
                Log.d("TAGTest", "error: ${error.message}")

            }
        )
        queue.add(jsonObjectRequest)
    }

    // test token example
    private fun testToken() {
        val url = "http://10.0.2.2:5000/api/users/email"
        // create a request queue
        val queue = Volley.newRequestQueue(this)

                val jsonObjectRequest = object : JsonObjectRequest(
                    Request.Method.GET, url, null,
            { resp ->

                Log.d("TOKENTEST", resp.toString())

            }, {
                    error ->
                Toast.makeText(this, "Email ou mot de passe incorrect !", Toast.LENGTH_SHORT).show()
                Log.d("TAGTest", "error: ${error.message}")

            }) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer ${loadToken()}")
                return headers }
            }

        queue.add(jsonObjectRequest)
    }


    // save email and password locally
    private fun saveData(email: String?, password: String?, username: String?, token : String) {
        val rememberMe = findViewById<CheckBox>(R.id.rememberMe)
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        if(rememberMe.isChecked){
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("EMAIL", email)
                putString("PASSWORD", password)
                putString("USERNAME", username)
                putString("TOKEN", token)
                putBoolean("check", rememberMe.isChecked)
            }.apply()
        }
        else{
            val editor = sharedPreferences.edit()
            editor.apply {
                putString("EMAIL", "email")
                putString("PASSWORD", "password")
                putString("USERNAME", username)
                putString("TOKEN", token)
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

    // load Email and password pre-recorded
    private fun loadToken() : String? {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("TOKEN", null)
    }
}