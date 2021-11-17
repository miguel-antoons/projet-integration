package com.example.smartfridge.android

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ForgotPasswordTest {
    @Test
    fun `ForgotPassword should start without crashing`() {
        val scenario = launchActivity<ForgotPassword>()
    }
}