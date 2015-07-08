package consumer;

import consumer.receiver.SimpleReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    static final String QUEUE_NAME = "tutorial-2";

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(QUEUE_NAME, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, Queue queue, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setMessageListener(listenerAdapter);
        //        container.setPrefetchCount(5);
        //        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(SimpleReceiver receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, new Jackson2JsonMessageConverter());
        messageListenerAdapter.setDefaultListenerMethod("receive");
        return messageListenerAdapter;
    }
}
