package com.example.smartfridge.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartfridge.android.fragments.FragmentHome
import com.example.smartfridge.android.fragments.FragmentProduct
import com.example.smartfridge.android.fragments.FragmentSettings
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launchActivity()
    }

    /**
    /!\ * the element we verify is displayed MUST be an object (button, ...) and not just text /!\
     */

    @Test
    fun `Fragment should change to FragmentHome on function call`() {
        // launch FragmentHome
        scenario.onActivity {
            it.setCurrentFragment(FragmentHome(it))
        }

        // check if an element on the FragmentHome fragment is displayed *
        onView(withId(R.id.fragment_button)).check(matches(isDisplayed()))
    }

    @Test
    fun `Fragment should change to FragmentProduct on function call`() {

        // launch FragmentProduct
        scenario.onActivity {
            it.setCurrentFragment(FragmentProduct(it))
        }

        // check if an element on the FragmentProduct fragment is displayed *
        onView(withId(R.id.addingBtn)).check(matches(isDisplayed()))
    }

    @Test
    fun `Fragment should change to FragmentSettings on function call`() {
        // launch FragmentSettings
        scenario.onActivity {
            it.setCurrentFragment(FragmentSettings(it))
        }

        // check if an element on the FragmentSettings fragment is displayed *
        onView(withId(R.id.new_location)).check(matches(isDisplayed()))
    }

    @Test
    fun `Fragment should change to FragmentProduct on button click`() {
        onView(withId(R.id.product_page)).perform(click())
        onView(withId(R.id.search_products)).check(matches(isDisplayed())) // *
    }

    @Test
    fun `Fragment should change to FragmentHome on button click`() {
        onView(withId(R.id.home_page)).perform(click())
        onView(withId(R.id.fragment_button)).check(matches(isDisplayed())) // *
    }

    @Test
    fun `Fragment should change to FragmentSettings on button click`() {
        onView(withId(R.id.settings_page)).perform(click())
        onView(withId(R.id.new_location)).check(matches(isDisplayed())) // *
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
}
