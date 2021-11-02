package com.example.smartfridge.android

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

// classes purpose is to keep all the products in it
// (it could also be used later to update the list with data from the database)
object ProductRepository {
    // list which contains all the products
    val productList = arrayListOf<ProductModel>()

    /**
     * Function performs all the necessary actions to add a product to the product list.
     * It was written specifically to work with a form to add a product
     * (cf. './FormsAddAliments.kt') but could be used for other purposes.
     */
    fun addProductFromForm(
        productName: String,
        productQuantity: Int,
        productExpirationDate: String,
        productCategory: String,
        productLocation: String) {
        // get 'Date' object from string date
        val expirationDate = convertToDate(productExpirationDate)
        // get the difference between today and expiration date in Long format
        val dateDifference = getDateDifference(expirationDate)
        // get the expiration period (i.e. '3 days')
        val expirationPeriod = convertDifferenceToString(dateDifference)
        // get the product color
        val productColor = getProductColor(dateDifference)

        // add the new product to the product list
        productList.add(ProductModel(
            productName,
            productQuantity,
            expirationDate,
            expirationPeriod,
            productCategory,
            productLocation,
            productColor
        ))
    }

    /**
     * Function performs all the necessary actions to update a product from the product list.
     * It was written specifically to work with a form to add a product
     * (cf. './FormsAddAliments.kt') but could be used for other purposes.
     */
    fun modifyProduct(
        productIndex: Int,
        productName: String,
        productQuantity: Int,
        productExpirationDate: String,
        productCategory: String,
        productLocation: String
    ) {
        // get 'Date' object from string date
        val expirationDate = convertToDate(productExpirationDate)
        // get the difference between today and expiration date in Long format
        val dateDifference = getDateDifference(expirationDate)
        // get the expiration period (i.e. '3 days')
        val expirationPeriod = convertDifferenceToString(dateDifference)
        // get the product color
        val productColor = getProductColor(dateDifference)

        // update the product with a method from the 'ProductModel' class
        productList[productIndex].updateProduct(
            productName,
            productQuantity,
            expirationDate,
            expirationPeriod,
            productCategory,
            productLocation,
            productColor
        )
    }

    // deletes an element from productList at a given index (productPosition)
    fun deleteProduct(productPosition: Int) {
        productList.removeAt(productPosition)
    }

    /**
     * Function converts a string date with the following format : 'dd / M / yyyy'
     * to a Kotlin Date object.
     * For this function to work correctly, the string date format MUST be respected.
     */
    private fun convertToDate(stringDate: String): Date {
        // convert expiration date to 'LocalDate' type
        val expirationLocalDate = LocalDate.parse(
            stringDate,
            DateTimeFormatter.ofPattern("dd / M / yyyy")
        )

        // converting expiration date to 'Date' type and returning it
        return Date
            .from(expirationLocalDate
                .atStartOfDay()
                .atZone(
                    ZoneId
                        .systemDefault()
                )
                .toInstant())
    }

    /**
     * Function calculates the difference between a 'Date' object and the current date.
     * It then returns the difference in days and is precise to the millisecond.
     */
    private fun getDateDifference(expirationDate: Date): Long {
        // get current date date in 'Long' format
        val currentDate: Long = Date().time
        val longExpirationDate: Long = expirationDate.time

        // get difference in days between the current date and the expiration date
        // return the result
        return ((longExpirationDate - currentDate) / (24 * 3600 * 1000))
    }

    /**
     * Function returns a string phrase with the number of days/months/years a long number
     * represents.
     * return examples : '2 ans', '9 mois', '1 jour', ...
     */
    private fun convertDifferenceToString(dateDifference: Long): String {
        when {
            // check if the period is greater than a year
            dateDifference >= 365 -> {
                // calculate the number of years
                val yearDifference: Int = (dateDifference / 365).toInt()
                return (
                        // return the phrase in singular/plural form
                        if (yearDifference <= 1) {
                            "$yearDifference an"
                        }
                        else {
                            "$yearDifference ans"
                        })
            }
            // check if the period is greater than a month
            dateDifference >= 30 -> {
                // calculate the number of months
                val monthDifference: Int = (dateDifference / 30).toInt()
                return "$monthDifference month"
            }
            // check eventually if the period is greater than a day
            dateDifference > 0 -> {
                return (
                        // return the phrase in singular/plural form
                        if (dateDifference <= 1) {
                            "${kotlin.math.ceil(dateDifference.toDouble()).toInt()} jour"
                        }
                        else {
                            "${kotlin.math.ceil(dateDifference.toDouble()).toInt()} jours"
                        })
            }
            else -> {
                // if the difference is negative, just return '0 days'
                return "0 jours"
            }
        }
    }

    /**
     * Function returns a color according ot the number of days that 'dateDifference' represents.
     * The colors returned by this function were chosen following the maquette at the following
     * link : https://github.com/miguel-antoons/projet-integration/wiki/Maquette
     */
    private fun getProductColor(dateDifference: Long): String {
        return when {
            dateDifference <= 0 -> "#000000"
            dateDifference <= 1 -> "#FF0000"
            dateDifference <= 2 -> "#FF9700"
            else -> "#00A00F"
        }
    }
}
