import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:${project.properties["shadowPluginVersion"]}")
    }
}

apply(plugin = "com.github.johnrengelman.shadow")

plugins {
    application
    kotlin("jvm") version "1.3.50"
}

group = "ksat.tulover"
version = "1.0.0"

application {
    mainClassName = "ksat.tulover.MoneyTransferServerKt"
}

tasks.withType<ShadowJar> {
    baseName = "money-transfer-server"
    classifier = ""
    version = ""
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

repositories {
    mavenLocal()
    jcenter()
}

val logbackVersion: String by project
val junit5Version: String by project
val hamkrestVersion: String by project
val http4kversion: String by project
val mapDBVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.http4k", "http4k-core", http4kversion)
    implementation("org.http4k", "http4k-server-netty", http4kversion)
    implementation("org.http4k", "http4k-format-jackson", http4kversion)
    implementation("org.http4k", "http4k-contract", http4kversion)
    implementation("org.http4k", "http4k-client-apache", http4kversion)
    implementation("org.http4k", "http4k-testing-hamkrest", http4kversion)

    implementation("ch.qos.logback", "logback-classic", logbackVersion)

    implementation("org.mapdb", "mapdb", mapDBVersion)
    
    testImplementation(kotlin("test-junit"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junit5Version)
    testImplementation("org.junit.jupiter", "junit-jupiter-params", junit5Version)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junit5Version)
}
