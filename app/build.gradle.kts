plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.price_tag"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.price_tag"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.ui)
    implementation(Dependencies.material3)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.constraintLayoutCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.uiGraphics)
    implementation(Dependencies.uiToolingPreview)
    implementation(Dependencies.uiTooling)
    implementation(Dependencies.materialIconExtended)
    implementation(Dependencies.accompanistFlowlayout)

    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiConverter)

    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutineAndroid)

//    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.testJunit)
    androidTestImplementation(Dependencies.espressoCore)


    implementation(project(Modules.utilites))

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.constraintlayout.compose)

//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation(libs.coil.compose)
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.accompanist.flowlayout)

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}