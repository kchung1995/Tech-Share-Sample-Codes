import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.katfun"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val coroutinesVersion = "1.8.1"

val openFeignVersion = "4.1.2"
val resilience4jVersion = "3.1.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    // spring cloud starter
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$openFeignVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:$resilience4jVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
