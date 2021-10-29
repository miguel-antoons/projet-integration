package com.example.smartfridge.android

import java.util.*

// all the items that defines a product along with some default values
class ProductModel (
    var name: String = "Nom du Produit",
    var quantity: Number = 1,
    var expirationDate: Date = Date(),
    var expirationPeriod: String = "1 jour",
    var category: String = "None",
    var location: String = "Emplacement",
    var productColor: String = "#FFFFFF"
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
        this.expirationDate = productExpirationDate
        this.expirationPeriod = productExpirationPeriod
        this.category = productCategory
        this.location = productLocation
        this.productColor = productColor
    }
}