package com.example.smartfridge.android

import android.util.Log
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.security.SecureRandom


object Hashing {
    // initialize Argon2Kt and load the native library
    private val argon2Kt = Argon2Kt()

    // Function that executes the function to hash to password of the user
    fun passwordHash(passwordToHash: String) : String{
        // password to ByteArray
        val charset = Charsets.UTF_8
        val password = passwordToHash.toByteArray(charset)

        // hash a password
        val hashResult : Argon2KtResult = argon2Kt.hash(
            mode = Argon2Mode.ARGON2_I,
            password = password,
            salt = generateSalt(),
            tCostInIterations = 5,
            mCostInKibibyte = 65536
        )
        Log.d("hashPassword", hashResult.encodedOutputAsString())
        return hashResult.encodedOutputAsString()
    }

    // Function that executes the function to hash to password of the user
    fun verifyPasswordHash(passwordToVerify: String, passwordHash: String): Boolean {
        // password to ByteArray
        val charset = Charsets.UTF_8
        val password = passwordToVerify.toByteArray(charset)

        // verify a password against an encoded string representation

        return argon2Kt.verify(
            mode = Argon2Mode.ARGON2_I,
            encoded = passwordHash,
            password = password
        )
    }

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return bytes
    }

}