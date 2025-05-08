plugins {
    id("apps.conventions")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-amqp")
    testImplementation("org.springframework.boot:spring-boot-docker-compose")
}