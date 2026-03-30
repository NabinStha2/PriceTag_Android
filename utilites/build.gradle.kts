plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.utilites"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

}

// ✅ new compilerOptions DSL
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.material3)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testJunit)
    androidTestImplementation(Dependencies.espressoCore)
}