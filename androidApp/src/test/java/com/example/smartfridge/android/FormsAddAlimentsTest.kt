package com.example.smartfridge.android

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
import com.example.smartfridge.android.adapter.ProductAdapter
import com.example.smartfridge.android.api.NutritionValues
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertEquals
import org.mockito.Mockito
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import com.google.gson.JsonParser


@RunWith(AndroidJUnit4::class)
class FormsAddAlimentsTest {
    private lateinit var scenario: ActivityScenario<FormsAddAliments>
    private lateinit var server: MockWebServer

    // set test values
    private val testUser = "testUser"
    private val testProductName = "testProductName"
    private val testBrand = "testBrand"
    private val testQuantity = "testQuantity"
    private val testDate = "testDate"
    private val testWeight = "testWeight"
    private val testLocation = "testLocation"
    private val testCategory = "testCategory"

    @Before
    fun setUp() {
        scenario = launchActivity()
        server = MockWebServer()

        // mock the ProductAdapter in order to get ProductRepository to work correctly
        val adapter = mock<ProductAdapter>()

        // tell the mock what to return when adapter.notifyItemInserted is called
        doNothing().`when`(adapter).notifyItemInserted(Mockito.anyInt())

        // set correct values for ProductRepository
        ProductRepository.addProductAdapter(adapter)
        ProductRepository.serverUrl = server.url("").toString()
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Activity should fill form and click on add without errors`() {
        // set a response to return when the mock server is called
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("food_response.json").content))
        }

        // fill out the form and submit the response
        onView(withId(R.id.AlimentName)).perform(typeText("Test"), closeSoftKeyboard())
        onView(withId(R.id.AlimentQuantite)).perform(typeText("3"), closeSoftKeyboard())
        onView(withId(R.id.dateTv)).perform(setTextInTextView("11/11/2026"))
        onView(withId(R.id.button_add_aliment)).perform(scrollTo(), click())
    }

    @Test
    fun `Activity should post data to back-end`() {
        // post food to the mock server
        scenario.onActivity {
            ProductRepository.sendFoodToServer(
                it,
                testUser,
                testProductName,
                testBrand,
                testQuantity,
                listOf("test"),
                testDate,
                NutritionValues(),
                testWeight,
                testLocation,
                testCategory
            )
        }

        // convert the data sent to the mock server into json
        val recordedRequest = server.takeRequest()
        val recordedRequestBody = recordedRequest.body.readUtf8()
        val jsonData = JsonParser.parseString(recordedRequestBody).asJsonObject

        // verify if the data is correct
        assertEquals("POST", recordedRequest.method)
        assertEquals(
            jsonData.get("Utilisateur").toString().replace("\"",""), testUser
        )
        assertEquals(
            jsonData.get("Nom").toString().replace("\"",""), testProductName
        )
        assertEquals(
            jsonData.get("Marque").toString().replace("\"",""), testBrand
        )
        assertEquals(
            jsonData.get("Quantite").toString().replace("\"",""), testQuantity
        )
        assertEquals(
            jsonData.get("Date").toString().replace("\"",""), testDate
        )
        assertEquals(
            jsonData.get("Poids").toString().replace("\"",""), testWeight
        )
        assertEquals(
            jsonData.get("Lieu").toString().replace("\"",""), testLocation
        )
        assertEquals(
            jsonData.get("Categorie").toString().replace("\"",""), testCategory
        )
    }

    @Test
    fun `Activity should call modify api`() {
        scenario.onActivity {
            ProductRepository.modifyProduct(
                it,
                3,
                testUser,
                testProductName,
                testBrand,
                testQuantity,
                listOf("test"),
                testDate,
                NutritionValues(),
                testWeight,
                testLocation,
                testCategory,
                "abc1243"
            )
        }

        // convert the data sent to the mock server into json
        val recordedRequest = server.takeRequest()
        val recordedRequestBody = recordedRequest.body.readUtf8()
        val jsonData = JsonParser.parseString(recordedRequestBody).asJsonObject

        // verify if the data is correct
        assertEquals("POST", recordedRequest.method)
        assertEquals(
            jsonData.get("Utilisateur").toString().replace("\"",""), testUser
        )
        assertEquals(
            jsonData.get("Nom").toString().replace("\"",""), testProductName
        )
        assertEquals(
            jsonData.get("Marque").toString().replace("\"",""), testBrand
        )
        assertEquals(
            jsonData.get("Quantite").toString().replace("\"",""), testQuantity
        )
        assertEquals(
            jsonData.get("Date").toString().replace("\"",""), testDate
        )
        assertEquals(
            jsonData.get("Poids").toString().replace("\"",""), testWeight
        )
        assertEquals(
            jsonData.get("Lieu").toString().replace("\"",""), testLocation
        )
        assertEquals(
            jsonData.get("Categorie").toString().replace("\"",""), testCategory
        )
    }

    @Test
    fun `Activity should call delete food api`() {
        // insert a product to delete
        ProductRepository.addProductFromForm(
            "test",
            "3",
            "03/05/2026",
            "testCat",
            "test"
        )

        // delete the product
        scenario.onActivity {
            ProductRepository.deleteProduct(
                0,
                it,
                testUser,
                testProductName,
                testBrand,
                testQuantity,
                listOf("test"),
                testDate,
                NutritionValues(),
                testWeight,
                testLocation,
                testCategory
            )
        }

        // convert the data sent to the mock server into json
        val recordedRequest = server.takeRequest()
        val recordedRequestBody = recordedRequest.body.readUtf8()
        val jsonData = JsonParser.parseString(recordedRequestBody).asJsonObject

        // verify if the data is correct
        assertEquals("POST", recordedRequest.method)
        assertEquals(
            jsonData.get("Utilisateur").toString().replace("\"",""), testUser
        )
        assertEquals(
            jsonData.get("Nom").toString().replace("\"",""), testProductName
        )
        assertEquals(
            jsonData.get("Marque").toString().replace("\"",""), testBrand
        )
        assertEquals(
            jsonData.get("Quantite").toString().replace("\"",""), testQuantity
        )
        assertEquals(
            jsonData.get("Date").toString().replace("\"",""), testDate
        )
        assertEquals(
            jsonData.get("Poids").toString().replace("\"",""), testWeight
        )
        assertEquals(
            jsonData.get("Lieu").toString().replace("\"",""), testLocation
        )
        assertEquals(
            jsonData.get("Categorie").toString().replace("\"",""), testCategory
        )
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

    // function changes text in TextView element to text given as argument
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