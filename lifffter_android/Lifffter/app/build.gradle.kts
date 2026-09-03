plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.lifffter"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.lifffter"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Add-ons
    implementation(libs.androidx.compose.material.icons.core)

    // navigation
    implementation(libs.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)


    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)


    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.hilt.navigation.compose)

    // New navigation compose that supports type-safety
    implementation(libs.androidx.navigation.compose.v280)

    // kotlin serialization library
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.androidx.compose.bom.v20240600)) // Or latest
    implementation(libs.androidx.ui)
    implementation(libs.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity.compose.v190)

// ViewModel & Lifecycle State (For collectAsStateWithLifecycle)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Retrofit Core
    implementation(libs.retrofit2.retrofit)
// JSON Converter (Gson used here, but Moshi/Kotlinx Serialization are fine too)
    implementation(libs.converter.gson.v2110)

// OkHttp & Logging Interceptor
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Paging 3 Core
    implementation(libs.androidx.paging.runtime)
// Paging 3 Jetpack Compose Integration
    implementation(libs.androidx.paging.compose)

    // WorkManager Core (Kotlin + Coroutines support)
    implementation(libs.androidx.work.runtime.ktx)

// Hilt Integration specifically for injecting WorkManager
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler) // Note: This is androidx.hilt, not google.dagger

}