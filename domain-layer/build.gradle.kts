plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}

tasks.register("assembleDebug") {
    dependsOn("build")
}

tasks.register("assembleDebugUnitTest") {
    dependsOn("test")
}

dependencies {
    val kotlinCorutines = "1.8.0"
    val jUnitVersion = "4.13.2"
    val javaxInjextVersion = "1"
    val mapStructVersion = "1.4.2"
    val mapStructProcessorVersion = "1.4.2"

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCorutines")

    // MAPSTRUCT
    implementation("org.mapstruct:mapstruct:$mapStructVersion.Final")
    kapt("org.mapstruct:mapstruct-processor:$mapStructProcessorVersion.Final")

    // HILT
    implementation("javax.inject:javax.inject:$javaxInjextVersion")

    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("junit:junit:$jUnitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${kotlinCorutines}")
}