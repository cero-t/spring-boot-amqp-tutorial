package consumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {
    @RabbitListener(queues = "tutorial-1")
    public void receive(String message) {
        System.out.println(message);
    }
}
