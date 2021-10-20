package com.example.smartfridge.android

// classes purpose is to keep all the products in it
// (it could also be used later to update the list with data from the database)
class ProductRepository {
    object Singleton {
        val productList = arrayListOf<ProductModel>()
    }
}
