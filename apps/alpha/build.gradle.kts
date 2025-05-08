plugins {
    id("apps.conventions")
    id("org.springframework.boot") version "3.4.4"
}

dependencies {
    implementation(project(":apps:shared"))
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.springframework.boot:spring-boot-starter-amqp")

    testAndDevelopmentOnly("org.springframework.boot:spring-boot-docker-compose")
}

