plugins {
    java
    id("org.springframework.boot") version "3.4.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
