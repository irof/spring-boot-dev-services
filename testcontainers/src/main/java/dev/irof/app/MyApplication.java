package dev.irof.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootApplication
public class MyApplication {
    private static final Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(MyApplication.class, args);

        var jdbcClient = applicationContext.getBean(JdbcClient.class);
        var version = jdbcClient.sql("SELECT version()").query().singleValue();
        logger.info("version: {}", version);
    }
}
