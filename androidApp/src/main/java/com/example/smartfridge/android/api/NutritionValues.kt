package com.example.smartfridge.android.api

data class NutritionValues(
    val energy : String = "test",
    val fat : String = "test",
    val carbohydrate: String = "test",
    val protein : String = "test",
    val salt : String = "test"
){
    override fun toString(): String {
        return "[Energie: ${this.energy}, Graisses: ${this.fat}, Sucres: ${this.carbohydrate}, Proteines: ${this.protein}, Sel: ${this.salt}]"
    }
}

