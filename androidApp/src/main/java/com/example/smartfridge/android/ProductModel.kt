package com.example.smartfridge.android

import com.example.smartfridge.android.api.NutritionValues

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
    var ingredients: List<String> = listOf("NOT DEFINED"),
    var brand: String = "NOT DEFINED",
    var user: String = "NOT DEFINED",
    var ecoscore: String = "X",
    var nutriscore: String = "X"
)