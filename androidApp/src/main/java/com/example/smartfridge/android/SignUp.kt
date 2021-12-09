package com.example.smartfridge.android

import android.content.Intent
import android.os.Bundle
import android.text.InputType
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
import com.android.volley.toolbox.JsonArrayRequest
import com.example.smartfridge.android.Hashing.passwordHash
import java.util.regex.Pattern

/**
 * Class gets all the data from the sign up form and POST them into the MongoDB database.
 * First, there is an initialisation of all the data from the form.
 * Secondly, we work with the data, checking if the user has correctly put data into the field.
 * In conclusion, when data is correctly verified, it can be POST to the database.
 */

class SignUp : AppCompatActivity() {

    // Initialisation of the data for the form

    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var etEmail: EditText
    lateinit var etCheckBox1: CheckBox
    lateinit var etCheckBox2: CheckBox

    private val MIN_PASSWORD_LENGTH = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Get all the data from the form fields with the method findViewById
        etPassword = findViewById(R.id.password)
        etConfirmPassword = findViewById(R.id.confirm_password)
        etCheckBox1 = findViewById(R.id.checkBox1)
        etCheckBox2 = findViewById(R.id.checkBox2)

        /**
         * etCheckBox1 and etCheckBox2 allows the user the show his password or instead hide it.
         * To do that, a little checkbox is put juste on the right of the field form.
         * If it is checked, the password is in clear, if not it is hide.
         */
        etCheckBox1.setOnCheckedChangeListener { _, isChecked1 ->
            if (isChecked1) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                etPassword.setSelection(etPassword.getText().length)
            }
        }

        etCheckBox2.setOnCheckedChangeListener { _, isChecked2 ->
            if (isChecked2) {
                etConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else {
                etConfirmPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                etConfirmPassword.setSelection(etConfirmPassword.getText().length)
            }
        }

        // Button of validation / execute the validation of all the fields and call the function performSignUp()
        val buttonReturnUser = findViewById<Button>(R.id.button)
        buttonReturnUser.setOnClickListener {
            etUsername = findViewById(R.id.username)
            etPassword = findViewById(R.id.password)
            etConfirmPassword = findViewById(R.id.confirm_password)
            etEmail = findViewById(R.id.email)

            // If the validation of all the fields form returns true :
            if (validateInput()) {
                isUsernameExist(etUsername.text.toString())
            }
        }
    }


    /**
     * This function is tested just before the API request (POST).
     * It checks all the fields of the sign up form and return, after checking all of them, a boolean.
     * True if all the fields are correct, and false if it isn't.
     *
     * Checked :
     * - Username : not empty
     * - Password : minimum of 8 characters, contains at least one uppercase - lowercase - digit
     * - Confirm password : is equal to password
     * - Email : the right format
     */
    private fun validateInput(): Boolean {

        val uppercase = Pattern.compile("[A-Z]")
        val lowercase = Pattern.compile("[a-z]")
        val digit = Pattern.compile("[0-9]")

        if (etUsername.text.toString() == "") {
            etUsername.setError("Veuillez entrer un username !")
            return false
        }
        // Password
        if (etPassword.text.toString() == "") {
            etPassword.setError("Veuillez entrer un mot de passe !")
        }
        // Same password / verification
        if (etConfirmPassword.text.toString() == "") {
            etConfirmPassword.setError("Veuillez entrer un mot de passe !")
        }
        // Email checking
        if (etEmail.text.toString() == "") {
            etEmail.setError("Veuillez entrer un email !")
        }

        // Checking if the password contains or not at least one lowercase
        if (!lowercase.matcher(etPassword.text.toString()).find()) {
            etPassword.setError("Doit contenir au moins une minuscule !")
            return false
        }

        // Checking if the password contains or not at least one uppercase
        if (!uppercase.matcher(etPassword.text.toString()).find()) {
            etPassword.setError("Doit contenir au moins une majuscule !")
            return false
        }

        // Checking if the password contains or not at least one lowercase
        if (!digit.matcher(etPassword.text.toString()).find()) {
            etPassword.setError("Doit contenir au moins un chiffre !")
            return false
        }

        // Checking minimum password length, in this case minimum of 8 characters
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("La longueur du mot de passe doit contenir au moins " + MIN_PASSWORD_LENGTH + " charactères")
            return false
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
            etConfirmPassword.setError("La mot de passe ne correspond pas !")
            return false
        }

        // Checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Veuillez entrer une adresse mail valide !")
            return false
        }
        return true
    }

    /**
     * This function with the help of a GET API, will check if the password that the user put in the field is the
     * same as one in the database MongoDB. If it is the case, an error is generated.
     */
    private fun isUsernameExist(username: String) {

        val url = "http://10.0.2.2:5000/api/username_verfication/$username"
        // create a request queue²
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { resp ->

                val response = resp.getString("state")
                // email verification
                if (response == "false") {
                    isEmailExist(etEmail.text.toString())
                }
                else {
                    etUsername.setError("Nom d'utilisateur déjà pris !")
                }

            }, { error ->
                Log.d("TAGTest", "error: ${error.message}")
                Log.d("MainActivity", "Api call failed")

            }
        )
        queue.add(jsonObjectRequest)
    }

    /**
     * Same for the Email ...
     */
    private fun isEmailExist(email: String){

        val url = "http://10.0.2.2:5000/api/email_verfication/$email"
        // create a request queue
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { resp ->
                val response = resp.getString("state")
                // email verification
                if (response == "false") {
                    performSignUp()
                }
                else {
                    etEmail.setError("Adresse mail déjà prise !")
                }

            }, { error ->
                Log.d("TAGTest", "error: ${error.message}")
                Log.d("MainActivity", "Api call failed")

            }
        )
        queue.add(jsonObjectRequest)
    }

    // Determine if the email is valid or not -> return a boolean : true or false
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    // Hook click event
    private fun performSignUp() {
        // If all the fields are correct the go further

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
            postData.put("Locations", arrayListOf("Emplacement Temporaire"))

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, postUrl, postData,
            { response ->
                Log.d("responseCode", response.toString())
                Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show()

                // creation de notre intent
                val monIntent : Intent =  Intent(this,Login::class.java)
                // start MainActivity
                startActivity(monIntent)

            }
        ) { _ ->
            Toast.makeText(this, "Erreur !", Toast.LENGTH_SHORT).show()
            }
        requestQueue.add(jsonObjectRequest)

    }
}
