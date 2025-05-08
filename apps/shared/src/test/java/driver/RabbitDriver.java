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
    public void alphaに送信() {
        rabbitTemplate.convertAndSend("alpha", "hoge");
    }

    @Test
    public void bravoに送信() {
        rabbitTemplate.convertAndSend("bravo", "hoge");
    }
}
