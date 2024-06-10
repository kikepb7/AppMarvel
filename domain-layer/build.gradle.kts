plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

dependencies {
    val kotlinCorutines = "1.8.0"
    val jUnitVersion = "4.13.2"
    val javaxInjextVersion = "1"
    val mapStructVersion = "1.4.2"
    val mapStructProcessorVersion = "1.4.2"
    val mockkVersion = "1.13.11"
    val coroutinesTestVersion = "1.6.4"
    val turbineVersion = "1.0.0"

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCorutines")

    // MAPSTRUCT
    implementation("org.mapstruct:mapstruct:$mapStructVersion.Final")
    kapt("org.mapstruct:mapstruct-processor:$mapStructProcessorVersion.Final")

    // HILT
    implementation("javax.inject:javax.inject:$javaxInjextVersion")

    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTestVersion")
    testImplementation("app.cash.turbine:turbine:$turbineVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}