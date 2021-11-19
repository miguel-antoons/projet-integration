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
    implementation("junit:junit:4.12")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // Hashing password
    implementation ("at.favre.lib:bcrypt:0.9.0")

    // QR code new user
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:core:3.4.1")

    // android volley => API
    implementation("com.android.volley:volley:1.2.1")

    // Required -- JUnit 4 framework
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}