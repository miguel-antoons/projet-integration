package com.example.smartfridge.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.smartfridge.android.adapter.ProductAdapter
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyInt
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4::class)
class ProductRepositoryTest {
    // these are all test values
    private val testNames = arrayOf("testProduct", "testProduct2", "testProduct3")
    private val testQuantities = arrayOf("3", "3098", "-100")
    private val testDates = arrayOf(
        LocalDate.now(),
        LocalDate.now().plusDays(2),
        LocalDate.now().plusDays(100),
    )
    private val testPeriods = arrayOf("0 jours", "1 jour", "3 mois")
    private val testCategories = arrayOf("testCate", "testCat2", "testCat3")
    private val testLocations = arrayOf("testLoc", "testLoc2", "testLoc3")
    private val testColors = arrayOf("#000000", "#FF0000", "#00A00F")

    // variable converts 'LocalDate' object to string date with given format
    private val localeDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @Before
    fun setUp() {
        val adapter = mock<ProductAdapter>()

        doNothing().`when`(adapter).notifyItemInserted(anyInt())
        ProductRepository.addProductAdapter(adapter)
    }

    @Test
    fun `Adding products to productList`() {
        // go over the 3 different test data
        for (index in testNames.indices) {
            // convert the given date to a string
            val stringDate = localeDateFormatter.format(
                testDates[index]
            )

            // test the add function of the 'ProductRepository' object
            val productIndex = ProductRepository.addProductFromForm(
                testNames[index],
                testQuantities[index],
                stringDate,
                testCategories[index],
                testLocations[index]
            )

            // get the newly inserted element
            val testElement = ProductRepository.productList[productIndex]

            // verify all of the inserted values
            assertThat(testElement.name).isEqualTo(testNames[index])
            assertThat(testElement.quantity).isEqualTo(testQuantities[index])
            assertThat(testElement.expirationDate).isEqualTo(stringDate)
            assertThat(testElement.expirationPeriod).isEqualTo(testPeriods[index])
            assertThat(testElement.category).isEqualTo(testCategories[index])
            assertThat(testElement.location).isEqualTo(testLocations[index])
            assertThat(testElement.productColor).isEqualTo(testColors[index])
        }
    }
}