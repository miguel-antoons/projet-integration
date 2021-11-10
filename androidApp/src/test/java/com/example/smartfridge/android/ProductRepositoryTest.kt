package com.example.smartfridge.android

import junit.framework.TestCase
import com.google.common.truth.Truth.assertThat
import java.text.SimpleDateFormat
import java.util.*

class ProductRepositoryTest : TestCase() {
    fun testAddProductFromForm() {
        val currentDate = Date()
        val dateFormatter = SimpleDateFormat("d / M / yyyy", Locale.getDefault())
        val stringDate = dateFormatter.format(currentDate)
        ProductRepository.addProductFromForm(
            "testProduct",
            3,
            stringDate,
            "testCat",
            "testLoc"
        )
        val testElement = ProductRepository.productList[0]
        assertThat(testElement.name).isEqualTo("testProduct")
        assertThat(testElement.quantity).isEqualTo(3)
        assertThat(testElement.expirationDate).isEqualTo(currentDate)
        assertThat(testElement.expirationPeriod).isEqualTo("0 jours")
        assertThat(testElement.category).isEqualTo("testCat")
        assertThat(testElement.location).isEqualTo("testLoc")
        assertThat(testElement.productColor).isEqualTo("#FFFFFF")
    }

    fun testModifyProduct() {}

    fun testDeleteProduct() {}
}