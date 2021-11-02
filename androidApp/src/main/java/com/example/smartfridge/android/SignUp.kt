package com.example.smartfridge.android

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {

    lateinit var etUsername : EditText
    lateinit var etPassword : EditText
    lateinit var etConfirmPassword : EditText
    lateinit var etEmail : EditText
    val MIN_PASSWORD_LENGTH = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        viewInitializations()

    }

    fun viewInitializations() {
        etUsername = findViewById(R.id.username)
        etPassword = findViewById(R.id.password)
        etConfirmPassword = findViewById(R.id.confirm_password)
        etEmail = findViewById(R.id.email)

    }


    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (etUsername.text.toString() == "") {
            etUsername.setError("Please enter a username")
            return false
        }
        if (etPassword.text.toString() == "") {
            etPassword.setError("Please enter a password")
        }
        if (etConfirmPassword.text.toString() == "") {
            etConfirmPassword.setError("Please enter a password")
        }
        if (etEmail.text.toString() == "") {
            etEmail.setError("Please enter a password")
        }

        // Checking minimum password length
        if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password length must be more than " + MIN_PASSWORD_LENGTH + "characters")
            return false
        }

        // Checking if repeat password is same
        if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
            etConfirmPassword.setError("Password does not match")
            return false
        }

        // Checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please enter a valid email")
            return false
        }

        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Hook click event
    fun performSignUp (view: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val email = etEmail.text.toString()

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            // Here you can call your API
        }
    }

}
