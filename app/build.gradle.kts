plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")

    // HILT
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    val espressoVersion = "3.5.1"

    // HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation(project(":data-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":ui-layer"))

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}