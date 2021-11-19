package com.example.smartfridge.android

import at.favre.lib.crypto.bcrypt.BCrypt

object Hashing {

    // Function that executes the function to hash to password of the user
    fun verifyPasswordHash(password : String, passwordHash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), passwordHash)

        if (result.verified) {
            return true
        }
        return false
    }

    // Function that executes the function to hash to password of the user
    fun passwordHash(password: String): String {
        val bcryptHashString =
            BCrypt.withDefaults().hashToString(12, password.toCharArray())
        val result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString)

        if (result.verified) {
            return bcryptHashString.toString()
        }
        return "1"
    }
}