package dev.irof.app.dbaccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@ActiveProfiles("compose")
public class DbAccessTest {

    @Autowired
    JdbcClient jdbcClient;

    @Test
    void DBアクセスできること() {
        var value = jdbcClient.sql("SELECT 'postgres'").query().singleValue();

        assertEquals("postgres", value);
    }
}
