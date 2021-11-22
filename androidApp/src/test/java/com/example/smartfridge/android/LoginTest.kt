package com.example.smartfridge.android

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @Test
    fun `Login should start without crashing`() {
        val scenario = launchActivity<Login>()
    }

    @Test
    fun validateEmailPassword() {
        Assert.assertTrue(VerifyEmailPassword.validateForm("example@gmal.com", "test1234test"))
        Assert.assertFalse(VerifyEmailPassword.validateForm("example@gmal.com", "test1"))
        Assert.assertFalse(VerifyEmailPassword.validateForm("", "test1"))
        Assert.assertFalse(VerifyEmailPassword.validateForm("example@gmal.com", ""))
    }
}