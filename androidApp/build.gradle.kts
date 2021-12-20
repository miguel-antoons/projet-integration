plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("kotlin-android-extensions")
    jacoco
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // Hashing password
    implementation ("at.favre.lib:bcrypt:0.9.0")

    // QR code new user
    implementation("com.google.zxing:core:3.4.1")

    // android volley => API
    implementation("com.android.volley:volley:1.2.1")

    // Required -- JUnit 4 framework
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("com.android.support.test.espresso:espresso-contrib:3.0.2") {
        exclude(mapOf("group" to "com.android.support", "module" to "support-annotations"))
        exclude(mapOf("group" to "com.android.support", "module" to "support-v4"))
        exclude(mapOf("group" to "com.android.support", "module" to "design"))
        exclude(mapOf("group" to "com.android.support", "module" to "recyclerview-v7"))
    }


    //Gson
    implementation("com.google.code.gson:gson:2.8.9")

    testImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("com.google.truth:truth:1.1")
    testImplementation("androidx.test:core-ktx:1.4.0")
    testImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("org.robolectric:robolectric:4.2.1")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("androidx.test.espresso:espresso-core:3.4.0")
    testImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    testImplementation("org.apache.httpcomponents:httpclient:4.5.6")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.6.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:2.13.0")

    // Argon2
    implementation("com.lambdapioneer.argon2kt:argon2kt:1.3.0")

    // Bar code
    implementation("com.google.mlkit:barcode-scanning:17.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.camera:camera-camera2:1.0.2")
    implementation("androidx.camera:camera-lifecycle:1.0.2")
    implementation("androidx.camera:camera-view:1.0.0-alpha31")

    // end2end
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation ("com.android.support.test:rules:1.0.2")
    androidTestImplementation ("com.android.support.test.espresso:espresso-intents:3.0.2")
    implementation("androidx.recyclerview:recyclerview:1.2.1")





}


android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.example.smartfridge.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        // Sets Java compatibility to Java 11
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
        jvmTarget = "11"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }

}