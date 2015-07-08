package producer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import producer.sender.SimpleSender;

import java.io.IOException;

@SpringBootApplication
public class ProducerApplication {
    static final String EXCHANGE_NAME = "tutorial-3-exchange";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
        SimpleSender sender = context.getBean(SimpleSender.class);
        sender.send();
        context.close();
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Exchange exchange(RabbitAdmin rabbitAdmin) {
        FanoutExchange exchange = new FanoutExchange(EXCHANGE_NAME);
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) throws IOException {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
