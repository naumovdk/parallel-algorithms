import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("me.champeau.gradle.jmh") version "0.5.0"
}

group = "me.dimna"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}


jmh {
    include = listOf("benchmarks.BenchmarkRunner.*")
    warmupIterations = 1
    iterations = 1
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}