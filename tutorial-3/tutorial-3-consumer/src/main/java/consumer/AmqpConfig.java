package consumer;

import com.rabbitmq.client.Channel;
import consumer.receiver.SimpleReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AmqpConfig {
    final static String exchange = "tutorial-3-exchange";

    @Bean
    Queue queue(ConnectionFactory connectionFactory) throws IOException {
        Connection connection = null;
        Channel channel = null;
        String queueName = null;
        try {
            connection = connectionFactory.createConnection();
            channel = connection.createChannel(false);
            queueName = channel.queueDeclare()
                    .getQueue();
        } finally {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return new Queue(queueName, true);
    }

    @Bean
    Binding binding(Queue queue) {
        return BindingBuilder.bind(queue)
                .to(new FanoutExchange(exchange));
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
