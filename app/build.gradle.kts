plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
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
    val navVersion = "2.7.7"
    val hiltVersion = "2.50"
    val room = "2.6.1"
    val retrofit = "2.9.0"
    val viewModel = "2.7.0"
    val httpLogging = "4.9.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    /* LIVE DATA */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    /* FRAGMENT */
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    /* NAVIGATION */
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    /* VIEWMODEL */
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModel")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModel")
    implementation("androidx.activity:activity-ktx:1.8.2")

    /* GLIDE */
    implementation("com.github.bumptech.glide:glide:4.16.0")

    /* COIL */
    implementation("io.coil-kt:coil:2.6.0")

    /* ROOM */
    implementation("androidx.room:room-ktx:$room")
    kapt("androidx.room:room-compiler:$room")

    /* DAGGER HILT */
    implementation ("com.google.dagger:hilt-android:$hiltVersion")
    kapt ("com.google.dagger:hilt-compiler:$hiltVersion")

    /* Retrofit */
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")

    /* HttpLogging */
    implementation("com.squareup.okhttp3:logging-interceptor:$httpLogging")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}