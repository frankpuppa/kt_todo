
val ktor_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

group = "com.todo"
version = "0.0.1"

application {
    mainClass.set("com.todo.test.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("com.zaxxer:HikariCP:5.1.0")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    this.testLogging {
        this.showStandardStreams = true
    }
    useJUnitPlatform()
}
