package consumer;

import consumer.receiver.SimpleReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AmqpConfig {
    static final String EXCHANGE_NAME = "tutorial-3-exchange";

    @Bean
    Queue queue(ConnectionFactory connectionFactory) throws IOException {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin.declareQueue();
    }

    @Bean
    FanoutExchange exchange(ConnectionFactory connectionFactory) {
        FanoutExchange exchange = new FanoutExchange(EXCHANGE_NAME);
        new RabbitAdmin(connectionFactory).declareExchange(exchange);
        return exchange;
    }


    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, Queue queue, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(SimpleReceiver receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, new Jackson2JsonMessageConverter());
        messageListenerAdapter.setDefaultListenerMethod("receive");
        return messageListenerAdapter;
    }
}
