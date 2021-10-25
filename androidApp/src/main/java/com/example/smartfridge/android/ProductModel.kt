package com.example.smartfridge.android

import java.util.*

// all the items that defines a product along with some default values
class ProductModel (
    val name: String = "Nom du Produit",
    val quantity: Number = 1,
    val expirationDate: Date = Date(),
    val expirationPeriod: String = "1 day",
    val category: String = "None",
    val location: String = "Emplacement",
    val productColor: String = "#FFFFFF"
)