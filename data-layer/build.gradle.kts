plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.enriquepalmadev.data_layer"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}

dependencies {
    val appCompatVersion = "1.6.1"
    val materialVersion = "1.12.0"
    val jUnitVersion = "4.13.2"
    val jUnitTestVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val mapStructVersion = "1.4.2"
    val mapStructProcessorVersion = "1.4.2"
    val retrofitVersion = "2.11.0"
    val interceptorVersion = "4.12.0"
    val hiltVersion = "2.51"

    implementation(project(":domain-layer"))

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")

    // MAPSTRUCT
    implementation("org.mapstruct:mapstruct:$mapStructVersion.Final")
    kapt("org.mapstruct:mapstruct-processor:$mapStructProcessorVersion.Final")

    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // INTERCEPTOR
    implementation("com.squareup.okhttp3:logging-interceptor:$interceptorVersion")

    // HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // TEST
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("io.mockk:mockk:1.12.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.37")
    kaptAndroidTest("com.google.dagger:hilt-android-testing:2.37")

    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}