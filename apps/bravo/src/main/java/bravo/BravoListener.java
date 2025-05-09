package bravo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;


@Component
public class BravoListener {
    private static final Logger logger = LoggerFactory.getLogger(BravoListener.class);

    private final JdbcClient jdbcClient;

    public BravoListener(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Bean
    Queue queue() {
        return new Queue("bravo");
    }

    @RabbitListener(queues = "bravo")
    public void receive(Message message) {
        logger.info("received: {}", message);

        jdbcClient.sql("INSERT INTO sample VALUES (:id)")
                .param("id", new String(message.getBody()))
                .update();

        var list = jdbcClient.sql("SELECT id FROM sample").query().listOfRows();
        logger.info("list: {}", list);
    }
}
