import org.gradle.kotlin.dsl.provideDelegate

object Dependencies {

    // Core
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }

    // Lifecycle
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}" }
    val lifecycleRuntimeComposeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleRuntimeKtx}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleRuntimeKtx}" }

    // Activity
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }

    // Compose BOM
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }

    // Compose UI — no version, managed by BOM
    val ui by lazy { "androidx.compose.ui:ui" }
    val uiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val uiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val uiTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val uiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
    val material3 by lazy { "androidx.compose.material3:material3" }
    val materialIconExtended by lazy { "androidx.compose.material:material-icons-extended" }

    // Navigation
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }

    // ConstraintLayout
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutCompose}" }

    // Coil
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.coilCompose}" }

    // Accompanist
    val accompanistFlowlayout by lazy { "com.google.accompanist:accompanist-flowlayout:${Versions.accompanistFlowlayout}" }

    // Hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroid}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }

    // Network
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val retrofitConverterMoshi by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okHttp}" }
    val okHttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}" }
    val moshi by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }

    // Coroutines
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }

    // Test
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val testJunit by lazy { "androidx.test.ext:junit:${Versions.junitVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }
}

object Modules {
    const val utilites = ":utilites"
}