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
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import java.util.regex.Pattern
import com.example.smartfridge.android.Hashing.passwordHash

class UpdatePassword : AppCompatActivity() {


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

            isValidPassword(firstpassword.text.toString())
        }


        val button_check_strenght_2 = findViewById<Button>(R.id.button_check_password2)
        button_check_strenght_2.setOnClickListener {

            isValidPassword(secondepassword.text.toString())
        }


        //Check if password 1 equals password 2
        val button_confirm = findViewById<Button>(R.id.submit)

        //Check password 1 and 2 srenght

        button_confirm.setOnClickListener {

            passwords_strenghts(firstpassword.text.toString(), secondepassword.text.toString())


        }

    }

    /**
     * @author  : Ben-Tahri Merwane
     * Function : passwords_strenghts
     * this function passwords_strenghts , checks if both passwords respect the rules for a strong password
     * @param -password1- : its a first password edittext string
     * @param -password2- : its a second password edittext string
     *
     * @return return a console message if its good or not
     *
     */

    fun passwords_strenghts(password1: String?, password2: String?) {
        if (isValidPassword(password1.toString()).equals(true) && isValidPassword(password2.toString()).equals(
                true
            )
        ) {

            Log.d("MainActivity", "Tout est Bon")
            check_equals_password(password1.toString(), password2.toString())


        } else {
            Log.d("MainActivity", "Mauvais mot de passe")


        }


    }


    /**
     * @author  : Ben-Tahri Merwane
     * Function : check_equals_password
     * This function check_equals_password : check if my first password equals my seconds password
     *
     * @param -password1- : its a first password edittext string
     * @param -password2- : its a second password edittext string
     *
     */


    fun check_equals_password(password1: String?, password2: String?) {

        if (password1.toString().equals(password2.toString())) {
            //get email from previous activity
            //Recover the email from the previous activity
            val email = intent
                .getStringExtra("Email")

            update_password(email, password1.toString())

            Toast.makeText(
                this,
                "les mot de passe sont correctes ",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("MainActivity", "$password1")
            Log.d("MainActivity", "Les mots de passe sont correctes et respectents les conditions")


        } else {

            Toast.makeText(
                this,
                "les mot de passe ne sont pas similaires",
                Toast.LENGTH_SHORT
            ).show()


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
    fun isValidPassword(password1: String?): Boolean {

        // Display password Strenght
        val atoz = findViewById<TextView>(R.id.atoz);
        val AtoZ = findViewById<TextView>(R.id.AtoZ);
        val num = findViewById<TextView>(R.id.num);
        val charcount = findViewById<TextView>(R.id.charcount);

        val password = password1.toString()

        // check for pattern
        // check for pattern
        val uppercase = Pattern.compile("[A-Z]")
        val lowercase = Pattern.compile("[a-z]")
        val digit = Pattern.compile("[0-9]")

        // if lowercase character is not present

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED)
            Log.d("MainActivity", "Pas de Minuscules")
            return false
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.GREEN)
        }

        // if uppercase character is not present

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            Log.d("MainActivity", "Pas de Majuscules")
            AtoZ.setTextColor(Color.RED)
            return false
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.GREEN)
        }
        // if digit is not present
        // if digit is not present
        if (!digit.matcher(password).find()) {
            Log.d("MainActivity", "Pas de chiffres")
            num.setTextColor(Color.RED)
            return false
        } else {
            // if digit is present
            num.setTextColor(Color.GREEN)
        }

        // if password length is less than 8
        if (password.length < 8) {
            Log.d("MainActivity", "Trop court")
            charcount.setTextColor(Color.RED)
            return false

        } else {
            charcount.setTextColor(Color.GREEN)
        }

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
        val password_hash = passwordHash(password.toString())
        Log.d("MainActivity", "$password_hash")


        // api route
        val url =
            "http://10.0.2.2:5000/api/users/reset-password/update-password/$email/$password_hash"
        // 10.0.2.2 Special alias to your host loopback interface (i.e., 127.0.0.1 on your development machine)

        // create a request queue
        val queue = Volley.newRequestQueue(this)

        // A request for retrieving a JSONArray response body at a given URL
        val jsonObjectRequest = JsonArrayRequest(

            // Type method
            Request.Method.PUT, url, null,
            { response ->

                if (response[0] == "Password Update IS ok") {

                    Toast.makeText(
                        this,
                        "Mot de passe mis à jour pour : $email",
                        Toast.LENGTH_SHORT
                    ).show()


                    val i = Intent(this, Login::class.java)
                    startActivity(i)


                } else {

                    setContentView(R.layout.activity_update_password)


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
