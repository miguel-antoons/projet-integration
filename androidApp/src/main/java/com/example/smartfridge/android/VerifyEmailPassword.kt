package com.example.smartfridge.android

object VerifyEmailPassword {
    // check if email and password are correct
    fun validateForm(email: String?, password: String?): Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >=6

        return isValidEmail && isValidPassword
    }

}