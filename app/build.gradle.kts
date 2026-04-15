plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.price_tag"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.price_tag"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {

    // ---------------- Core ----------------
    implementation(libs.core.ktx)

    // ---------------- Lifecycle ----------------
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.compose)

    // ---------------- Activity ----------------
    implementation(libs.activity.compose)

    // ---------------- Compose ----------------
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.preview)
    implementation(libs.material3)
    implementation(libs.material.icons)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test)

    // ---------------- Navigation ----------------
    implementation(libs.navigation.compose)

    // ---------------- Layout ----------------
    implementation(libs.constraintlayout)

    // ---------------- Image ----------------
    implementation(libs.coil)

    // ---------------- Accompanist ----------------
    implementation(libs.accompanist.flow)

    // ---------------- Hilt ----------------
    implementation(libs.hilt)
    implementation(libs.hilt.nav)
    ksp(libs.hilt.compiler)

    // ---------------- Network ----------------
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi)

    // ---------------- Coroutines ----------------
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // ---------------- Testing ----------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.espresso)

    // ---------------- Navigation 3 ---------------
//    implementation(libs.androidx.navigation3.ui)
//    implementation(libs.androidx.navigation3.runtime)
//    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
//    implementation(libs.androidx.material3.adaptive.navigation3)
    implementation(libs.kotlinx.serialization.core)

    // ---------------- Module ----------------
    implementation(project(":utilites"))
}