package dev.irof.app.dbaccess;

import dev.irof.app.MyContainerJdbcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MyContainerJdbcTest
public class DbAccessTest {

    @Autowired
    JdbcClient jdbcClient;

    @Test
    void DBアクセスできること() {
        var value = jdbcClient.sql("SELECT 'mysql'").query().singleValue();

        assertEquals("mysql", value);
    }
}
