package driver;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitDriver {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void defaultExchange経由でalphaに送信() {
        rabbitTemplate.convertAndSend("alpha", "alpha:" + System.currentTimeMillis());
    }

    @Test
    public void defaultExchange経由でbravoに送信() {
        rabbitTemplate.convertAndSend("bravo", "bravo:" + System.currentTimeMillis());
    }
}
