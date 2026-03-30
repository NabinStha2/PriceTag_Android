plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
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
        vectorDrawables { useSupportLibrary = true }
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

    buildFeatures { compose = true }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleRuntimeComposeKtx)
    implementation(Dependencies.activityCompose)

    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.ui)
    implementation(Dependencies.uiGraphics)
    implementation(Dependencies.uiToolingPreview)
    implementation(Dependencies.uiTooling)
    implementation(Dependencies.material3)
    implementation(Dependencies.materialIconExtended)

    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.constraintLayoutCompose)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.accompanistFlowlayout)

    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltNavigationCompose)
    ksp(Dependencies.hiltAndroidCompiler)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitConverterMoshi)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.moshi)

    implementation(Dependencies.coroutineCore)
    implementation(Dependencies.coroutineAndroid)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testJunit)
    androidTestImplementation(Dependencies.espressoCore)
    debugImplementation(Dependencies.uiTestManifest)

    implementation(project(Modules.utilites))
}