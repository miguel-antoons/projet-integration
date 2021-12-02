package com.example.smartfridge.android.api

import com.google.gson.Gson

data class NutritionValues(
    val energy : String = "test",
    val fat : String = "test",
    val carbohydrate: String = "test",
    val protein : String = "test",
    val salt : String = "test"
){
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(NutritionValues())
    }
}

