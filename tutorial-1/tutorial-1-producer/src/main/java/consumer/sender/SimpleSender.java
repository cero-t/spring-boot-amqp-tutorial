package consumer.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() {
        rabbitTemplate.convertAndSend("tutorial-1", "Hello World!");
    }
}
