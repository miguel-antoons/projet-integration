package com.example.smartfridge.android.fragments

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.smartfridge.android.SignUp


@RunWith(AndroidJUnit4::class)
class SignUpTest {
    private lateinit var scenario: ActivityScenario<SignUp>
    private lateinit var server: MockWebServer

    /*// set test values
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var etEmail: EditText*/

    @Before
    fun setup() {
        scenario = launchActivity()
        server = MockWebServer()
    }

    @After
    fun shutdown() {
        server.shutdown()
    }


    /** DOESN'T WORK
    @Test
    fun `Activity should fill form and click on add without errors`() {
        // set a response to return when the mock server is called
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("user_response.json").content))
        }

        // fill out the form and submit the response
        onView(withId(R.id.username)).perform(typeText("Eliott"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("Test1234"), closeSoftKeyboard())
        onView(withId(R.id.confirm_password)).perform(typeText("Test1234"), closeSoftKeyboard())
        onView(withId(R.id.email)).perform(typeText("test@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(scrollTo(), click())
    }**/

    // If a device is low on resources, the system might destroy an activity, requiring the app to
    // recreate that activity when the user returns to your app. We use then recreate()
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
    fun testEvent() {
            val scenario = launchActivity<SignUp>()
            scenario.recreate()
        }
    }
