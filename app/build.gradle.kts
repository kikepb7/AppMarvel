plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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

    buildFeatures{
        viewBinding = true
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
    val materialVersion = "1.11.0"
    val constraintLayoutVersion = "2.1.4"
    val legacySupportVersion = "1.0.0"
    val liveDataVersion = "2.7.0"
    val fragmentVersion = "1.6.2"
    val navVersion = "2.7.7"
    val viewModelVersion = "2.7.0"
    val glideVersion = "4.16.0"
    val coilVersion = "2.6.0"
    val jUnitVersion = "4.13.2"
    val jUnitTestVersion = "1.1.5"
    val espressoVersion = "3.5.1"

    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.legacy:legacy-support-v4:$legacySupportVersion")

    /* LIVE DATA */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$liveDataVersion")

    /* FRAGMENT */
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")

    /* NAVIGATION */
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    /* VIEWMODEL */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")

    /* GLIDE */
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    /* COIL */
    implementation("io.coil-kt:coil:$coilVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}