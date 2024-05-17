plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")

}
java {

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}


dependencies {
    val kotlinCorutines = "1.6.1"
    val jUnitVersion = "4.13.2"
    val mapStructVersion = "1.4.2"
    val mapStructProcessorVersion = "1.4.2"

    // COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCorutines")

    // MAPSTRUCT
    implementation("org.mapstruct:mapstruct:$mapStructVersion.Final")
    kapt("org.mapstruct:mapstruct-processor:$mapStructProcessorVersion.Final")

    // HILT
    implementation("javax.inject:javax.inject:1")

    testImplementation("junit:junit:$jUnitVersion")
}