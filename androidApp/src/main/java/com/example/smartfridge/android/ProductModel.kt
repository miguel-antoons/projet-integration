package com.example.smartfridge.android

import com.example.smartfridge.android.api.NutritionValues
import java.util.*

// all the items that defines a product along with some default values
class ProductModel(
    var name: String = "Nom du Produit",
    var quantity: Int = 1,
    var expirationDate: String = "26/10/2021",
    var expirationPeriod: String = "1 jour",
    var category: String = "None",
    var location: String = "Emplacement",
    var productColor: String = "#FFFFFF",
    var nutritiveValues: NutritionValues ?= NutritionValues(),
    var ingredients: Array<String> ?= arrayOf("NOT DEFINED"),
    var brand: String ?= "NOT DEFINED",
    var weight: String? = "NOT DEFINED",
    var user: String ?= "NOT DEFINED"
) {
    fun updateProduct(
        productName: String,
        productQuantity: Int,
        productExpirationDate: Date,
        productExpirationPeriod: String,
        productCategory: String,
        productLocation: String,
        productColor: String
    ) {
        this.name = productName
        this.quantity = productQuantity
        this.expirationDate = productExpirationDate.toString()
        this.expirationPeriod = productExpirationPeriod
        this.category = productCategory
        this.location = productLocation
        this.productColor = productColor
    }
}