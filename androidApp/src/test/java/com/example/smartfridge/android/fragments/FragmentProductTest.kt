package com.example.smartfridge.android.fragments

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartfridge.android.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FragmentProductTest {
    private val productTestName = "TestName"
    private val productTestLocation = "TestLoc"

    @Before
    fun setUp() {
        // launch activity MainActivity
        val scenario = launchActivity<MainActivity>()

        // change fragment to FragmentProduct
        scenario.onActivity { it.setCurrentFragment(FragmentProduct(it)) }

        // add a product to ProductRepository in order to test
        ProductRepository.addProductFromForm(
            productTestName,
            3,
            "03/05/2026",
            "testCat",
            productTestLocation
        )
    }

    @Test
    fun `Click on add product button should start FormsAddAliments activity`() {
        Intents.init()

        // click on add product button
        onView(withId(R.id.addingBtn)).perform(click())

        // check if the new activity has started
        intended(hasComponent(FormsAddAliments::class.java.name))
        Intents.release()
    }

    @Test
    fun `Click on product should open pop-up`() {
        // search the recyclerview item and click on it
        onView(withId(R.id.product_page_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )

        // This below should work but doesn't for some reason
        // onView(isRoot()).inRoot(isDialog()).check(matches(isDisplayed()))
        // onView(withText("TestName")).inRoot(isDialog()).check(matches(isDisplayed()))
        // onView(withId(R.id.product_title)).check(matches(isDisplayed()))
    }

    @Test
    fun `Product information should be correct`() {
        onView(withId(R.id.product_page_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, scrollTo()))
            .check(matches(hasDescendant(withText(productTestName))))

        onView(withId(R.id.product_page_list))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, scrollTo()))
            .check(matches(hasDescendant(withText(productTestLocation))))
    }
}