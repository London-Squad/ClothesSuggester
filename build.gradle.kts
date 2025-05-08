plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.3"))
    implementation("io.insert-koin:koin-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("io.ktor:ktor-client-core:3.1.2")
    implementation("io.ktor:ktor-client-cio:3.1.2")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.12.1")
    testImplementation("io.ktor:ktor-client-mock:3.1.2")
    testImplementation("io.mockk:mockk:1.14.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}