plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // android voley => API
    implementation("com.android.volley:volley:1.2.1")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.example.smartfridge.android"
        minSdk = 16
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        // Sets Java compatibility to Java 8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}