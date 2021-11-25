package com.example.smartfridge.android

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HowToConnectTest {
    @Test
    fun `HowToConnect should start without crashing`() {
        val scenario = launchActivity<HowToConnect>()
    }
}