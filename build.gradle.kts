plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply true
    id("jacoco")
    application
}

group = "ru.hse.smartUniversity"
version = "1.5.31"

application {
    mainClass.set("ru.hse.smartUniversity.MainKt")
}

repositories {
    mavenCentral()
}

ktlint {
    enableExperimentalRules.set(true)
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version")
    implementation("org.jacoco:org.jacoco.core:0.8.7")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        csv.required.set(true)
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.hse.smartUniversity.MainKt"
    }
}
