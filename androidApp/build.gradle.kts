plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    testOptions {
        unitTests.isIncludeAndroidResources = true
        animationsDisabled = true
    }
}