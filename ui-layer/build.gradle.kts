plugins {
//    id("org.jetbrains.kotlin.android")

    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.enriquepalmadev.ui_layer"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


//    buildTypes {
//        release {
//            isMinifyEnabled = false
//        }
//    }

    kapt {
        correctErrorTypes = true
        generateStubs = true
    }

    buildFeatures {
        viewBinding = true
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
    val ktxVersion = "1.13.1"
    val appCompatVersion = "1.6.1"
    val materialVersion = "1.12.0"
    val constraintLayoutVersion = "2.1.4"
    val legacySupportVersion = "1.0.0"
    val fragmentVersion = "1.7.0"
    val navVersion = "2.7.7"
    val viewModelVersion = "2.7.0"
    val glideVersion = "4.16.0"
    val jUnitVersion = "4.13.2"
    val jUnitTestVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val hiltVersion = "2.51"
    val facebookVersion = "0.5.0"

    implementation(project(":domain-layer"))

    implementation("androidx.core:core-ktx:$ktxVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.legacy:legacy-support-v4:$legacySupportVersion")

    /* FACEBOOK */
    implementation("com.facebook.shimmer:shimmer:$facebookVersion")

    /* FRAGMENT */
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")

    /* NAVIGATION */
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    /* VIEWMODEL */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")

    /* GLIDE */
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    // HILT
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}