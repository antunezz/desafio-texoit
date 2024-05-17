plugins {
    id("java")
    id("io.qameta.allure-report") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.24.0"
val cucumberVersion = "7.13.0"

dependencies {
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-java")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit-platform")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.slf4j:slf4j-simple:1.7.30")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.20.0")
    testImplementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.apache.commons:commons-lang3:3.14.0")
    testImplementation("commons-io:commons-io:2.16.1")
}

tasks.test {
    useJUnitPlatform()
}

val allureResultsDir = "${projectDir}/allure-results"

tasks.register<Delete>("cleanAllureResults") {
    delete(fileTree(allureResultsDir))
}

tasks.named("test") {
    dependsOn("cleanAllureResults")
}
