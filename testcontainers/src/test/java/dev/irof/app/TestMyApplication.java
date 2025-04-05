package dev.irof.app;

import org.springframework.boot.SpringApplication;

public class TestMyApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(MyApplication::main)
                .with(MyContainersConfiguration.class)
                .run(args);
    }
}
