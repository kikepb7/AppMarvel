plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    // HILT
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.enriquepalmadev.domain_layer"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val appCompatVersion = "1.6.1"
    val materialVersion = "1.12.0"
    val jUnitVersion = "4.13.2"
    val hiltVersion = "2.48"
    val mapStructVersion = "1.4.2"
    val mapStructProcessorVersion = "1.4.2"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")

    /* MAPSTRUCT */
    implementation ("org.mapstruct:mapstruct:$mapStructVersion.Final")
    kapt ("org.mapstruct:mapstruct-processor:$mapStructProcessorVersion.Final")

    // HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    testImplementation("junit:junit:$jUnitVersion")
}