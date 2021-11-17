package com.example.smartfridge.android

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartfridge.android.fragments.FragmentProduct
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun `MainActivity should start without crashing`() {
        val scenario = launchActivity<MainActivity>()
    }
}

/*@RunWith(AndroidJUnit4::class)
class MyTestSuite {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test fun testEvent() {
        val scenario = activityScenarioRule.scenario
    }
}*/
