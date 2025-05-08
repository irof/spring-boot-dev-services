rootProject.name = "spring-boot-dev-services"
include("docker-compose", "testcontainers")
include("apps:alpha", "apps:bravo", "apps:shared")
