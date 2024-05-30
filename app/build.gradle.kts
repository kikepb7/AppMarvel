plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.enriquepalmadev.appmarvel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.enriquepalmadev.appmarvel"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kapt {
        correctErrorTypes = true
        generateStubs = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val hiltVersion = "2.51"
    val jUnitVersion = "4.13.2"
    val jUnitTestVersion = "1.1.5"
    val mockkVersion = "1.13.11"

    // HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation(project(":data-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":ui-layer"))

    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    testImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
}