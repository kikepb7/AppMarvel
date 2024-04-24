plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
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
    val retrofitVersion = "2.11.0"
    val loggingInterceptorVersion = "4.12.0"
    val mapStructVersion = "1.4.2"
    val facebookVersion = "0.5.0"
    val coroutinesVersion = "1.7.3"
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

    /* RETROFIT */
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    /* LOGGING INTERCEPTOR */
    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")

    /* CORRUTINAS */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    /* MAPSTRUCT */
    implementation ("org.mapstruct:mapstruct:$mapStructVersion.Final")

    /* FACEBOOK */
    implementation("com.facebook.shimmer:shimmer:$facebookVersion")

    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:$jUnitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")

}