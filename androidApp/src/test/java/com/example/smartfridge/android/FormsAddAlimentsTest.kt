package com.example.smartfridge.android

import android.util.JsonReader
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import com.example.smartfridge.android.api.NutritionValues
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.JsonParser
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class FormsAddAlimentsTest {
    private lateinit var scenario: ActivityScenario<FormsAddAliments>
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        scenario = launchActivity()
        server = MockWebServer()
        ProductRepository.serverUrl = server.url("").toString()
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Activity should fill form and click on add without errors`() {
        ProductRepository.serverUrl = server.url("").toString()
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("food_response.json").content))
        }
        onView(withId(R.id.AlimentName)).perform(typeText("Test"), closeSoftKeyboard())
        onView(withId(R.id.AlimentQuantite)).perform(typeText("3"), closeSoftKeyboard())
        onView(withId(R.id.dateTv)).perform(setTextInTextView(" 11/11/2026"))
        onView(withId(R.id.button_add_aliment)).perform(scrollTo(), click())
    }

    @Test
    fun `Activity should post data to back-end`() {
        scenario.onActivity {
            ProductRepository.sendFoodToServer(
                it,
                "TEst",
                "test",
                "test",
                "test",
                arrayOf("test"),
                "test",
                NutritionValues(),
                "test",
                "test",
                "test"
            )
        }
        val recordedRequest = server.takeRequest(5, TimeUnit.SECONDS)
        val recordedRequestBody = recordedRequest.body.readUtf8()
        // val jsonData = JSON.parse(recordedRequestBody)

        assertEquals("POST", recordedRequest?.method)
    }

    @Test
    fun `Activity should change state to create without error`() {
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun `Activity should change state to started without errors`() {
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun `Activity should change state to resumed without errors`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun `Activity should change state to destroyed without errors`() {
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun `Activity should recreate without errors`() {
        scenario.recreate()
    }

    private fun setTextInTextView(value: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(TextView::class.java))
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }
}