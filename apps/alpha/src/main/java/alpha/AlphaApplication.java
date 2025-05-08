package alpha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlphaApplication {
    private static final Logger logger = LoggerFactory.getLogger(AlphaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlphaApplication.class, args);
    }

    @Bean
    Queue queue() {
        return new Queue("alpha");
    }

    @RabbitListener(queues = "alpha")
    public void receive(Message message) {
        logger.info("received: {}", message);
    }
}
