package com.example.smartfridge.android

import com.example.smartfridge.android.api.NutritionValues
import java.time.LocalDateTime
import java.util.*

// all the items that defines a product along with some default values
class ProductModel(
    var id: String = "48ZD485",
    var name: String = "Nom du Produit",
    var quantity: String = "1",
    var expirationDate: String = "26/10/2021",
    var expirationPeriod: String = "1 jour",
    var category: String = "None",
    var location: String = "Emplacement",
    var productColor: String = "#FFFFFF",
    var nutritiveValues: NutritionValues ?= NutritionValues(),
    var ingredients: List<String> ?= listOf("NOT DEFINED"),
    var brand: String ?= "NOT DEFINED",
    var weight: String? = "NOT DEFINED",
    var user: String ?= "NOT DEFINED"
) {
    fun updateProduct(
        productName: String,
        productQuantity: String,
        productExpirationDate: Date,
        productExpirationPeriod: String,
        productCategory: String,
        productLocation: String,
        productColor: String,
        Ingredients: List<String>,
        Valeurs: NutritionValues,
        Poids: String,
        Marque: String,
        Utilisateur: String
    ) {
        this.name = productName
        this.quantity = productQuantity
        this.expirationDate = productExpirationDate.toString()
        this.expirationPeriod = productExpirationPeriod
        this.category = productCategory
        this.location = productLocation
        this.productColor = productColor
        this.nutritiveValues = Valeurs
        this.brand = Marque
        this.ingredients = Ingredients
        this.user = Utilisateur
        this.weight = Poids
    }
}