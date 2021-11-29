package com.example.smartfridge.android

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.util.regex.Pattern
import com.example.smartfridge.android.Hashing.passwordHash
import org.json.JSONException
import org.json.JSONObject

class UpdatePassword : AppCompatActivity() {
    private val MIN_PASSWORD_LENGTH = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        //get password editext
        val firstpassword = findViewById<EditText>(R.id.new_mpd)
        val secondepassword = findViewById<EditText>(R.id.confirm_new_mdp)



        //get email from previous activity
        //Recover the email from the previous activity
        val emailFromForgot = intent
            .getStringExtra("Email")

        //display email

        val tv_email = findViewById<TextView>(R.id.textView_email).apply {
            text = emailFromForgot
        }


        //CheckBox
        val checkbox1 = findViewById<CheckBox>(R.id.checkBox_password_1)
        val checkBox2 = findViewById<CheckBox>(R.id.checkBox_password_2)

        // add onCheckedListener on checkbox
        // when user clicks on this checkbox, this is the handler.

        checkbox1.setOnCheckedChangeListener { _, isChecked ->

            //check if checkbox is checked or not
            if (isChecked) {

                firstpassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()

                //checkbox is checked
            } else {
                //checkbox is not checked

                firstpassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->


            //check if checkbox is checked or not
            if (isChecked) {

                secondepassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()

                //checkbox is checked
            } else {
                //checkbox is not checked

                secondepassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        //Button Check strenght Password
        val button_check_strenght_1 = findViewById<Button>(R.id.button_check_password1)
        button_check_strenght_1.setOnClickListener {

            isValidPassword()
        }


        val button_check_strenght_2 = findViewById<Button>(R.id.button_check_password2)
        button_check_strenght_2.setOnClickListener {

            isValidPassword()
        }


        //Check if password 1 equals password 2
        val button_confirm = findViewById<Button>(R.id.submit)

        //Check password 1 and 2 srenght

        button_confirm.setOnClickListener {

            isValidPassword()


        }

    }



    /**
     *@author  : Ben-Tahri Merwane
     * FUNCTION : isValidPassword
     *
     * Checks if the password is valid as per the following password policy.
     * Password should be minimum minimum 8 characters long.
     * Password should contain at least one number.
     * Password should contain at least one capital letter.
     * Password should contain at least one small letter.
     * Password should contain at least one special character.
     * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
     *
     * @param -password- can be EditText or String
     *
     * @return - true if the password is valid as per the password policy.
     */
    fun isValidPassword(): Boolean {

        val password_edittext1 = findViewById<EditText>(R.id.new_mpd)
        val password_edittext2 = findViewById<EditText>(R.id.confirm_new_mdp)

        // check for pattern
        // check for pattern
        val uppercase = Pattern.compile("[A-Z]")
        val lowercase = Pattern.compile("[a-z]")
        val digit = Pattern.compile("[0-9]")

        // Password
        if (password_edittext1.text.toString() == "") {
            password_edittext1.setError("Veuillez entrer un mot de passe !")
        }
        // Same password / verification
        if (password_edittext2.text.toString() == "") {
            password_edittext2.setError("Veuillez entrer un mot de passe !")
        }

        // Checking if the password contains or not at least one lowercase
        if (!lowercase.matcher(password_edittext1.text.toString()).find()) {
            password_edittext1.setError("Doit contenir au moins une minuscule !")
            return false
        }

        // Checking if the password contains or not at least one uppercase
        if (!uppercase.matcher(password_edittext1.text.toString()).find()) {
            password_edittext1.setError("Doit contenir au moins une majuscule !")
            return false
        }

        // Checking if the password contains or not at least one lowercase
        if (!digit.matcher(password_edittext1.text.toString()).find()) {
            password_edittext1.setError("Doit contenir au moins un chiffre !")
            return false
        }

        // Checking minimum password length, in this case minimum of 8 characters
        if (password_edittext1.text.length < MIN_PASSWORD_LENGTH) {
            password_edittext1.setError("La longueur du mot de passe doit contenir au moins " + MIN_PASSWORD_LENGTH + " charactères")
            return false
        }

        // Checking if repeat password is same
        if (!password_edittext1.text.toString().equals(password_edittext2.text.toString())) {
            password_edittext2.setError("La mot de passe ne correspond pas !")
            return false
        }
        val emailFromForgot = intent
            .getStringExtra("Email")
        update_password(emailFromForgot,password_edittext2.text.toString())

        return true




    }

    /**
     * @author Ben-Tahri Merwane
     * This function update_password : update a password for a user in database with email
     * @param -email- can be EditText or String, its email user
     * @param -password- can be EditText or String, its password user
     *
     * @return - close activity when password changed
     */

    private fun update_password(email: String?, password: String?) {

        //hash password
        //val password_hash = passwordHash(password.toString())
        //Log.d("MainActivity", "$password_hash")

        val putUrl = "http://10.0.2.2:5000/api/users/update-password"
        val requestQueue = Volley.newRequestQueue(this)

        val putData = JSONObject()
        try {

            putData.put("Password", passwordHash(password.toString()))
            putData.put("Email", email)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.PUT, putUrl, putData,
            { response -> println(response)}



        ) {error -> error.printStackTrace()}
        requestQueue.add(jsonObjectRequest)
        Toast.makeText(
            this,
            "Mot de passe mis à jour pour : $email",
            Toast.LENGTH_SHORT
        ).show()


        val i = Intent(this, Login::class.java)
        startActivity(i)


    }
}
