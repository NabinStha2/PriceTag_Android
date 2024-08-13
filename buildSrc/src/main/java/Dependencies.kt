import org.gradle.kotlin.dsl.provideDelegate

object Dependencies {

    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val ui by lazy { "androidx.compose.ui:ui" }
    val uiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val uiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.ui}" }
    val uiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.ui}" }
    val material3 by lazy { "androidx.compose.material3:material3" }
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutCompose}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.coilCompose}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }
    val accompanistFlowlayout by lazy { "com.google.accompanist:accompanist-flowlayout:${Versions.accompanistFlowlayout}" }
    val materialIconExtended by lazy { "androidx.compose.material:material-icons-extended:" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }

    val junit by lazy { "androidx.junit:junit:${Versions.junit}" }
    val testJunit by lazy { "androidx.test.ext:junit:${Versions.junitVersion}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }

    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hiltAndroid}" }
    val hiltAndroidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }

    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okHttp}" }
    val okHttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}" }
    val moshi by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }
    val moshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}" }

    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}" }
    val lifecycleLivedata by lazy { "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycleLivedata}" }

    val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineAndroid}" }
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}" }

}


object Modules {
    const val utilites = ":utilites"
}