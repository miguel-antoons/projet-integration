package com.example.smartfridge.android

// all the items that defines a product
class ProductModel (
    val name: String = "Nom du Produit",
    val quantity: Number = 1,
    val expirationDate: String = "1 day",
    val category: String = "None",
    val location: String = "Emplacement"
)