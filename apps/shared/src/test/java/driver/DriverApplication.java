package driver;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DriverApplication {

    @Bean
    TopicExchange sharedTopicExchange() {
        return new TopicExchange("shared-exchange");
    }

    @Bean
    Binding alphaBinding() {
        return BindingBuilder.bind(new Queue("alpha")).to(sharedTopicExchange()).with("#");
    }

    @Bean
    Binding bravoBinding() {
        return BindingBuilder.bind(new Queue("bravo")).to(sharedTopicExchange()).with("#");
    }
}
