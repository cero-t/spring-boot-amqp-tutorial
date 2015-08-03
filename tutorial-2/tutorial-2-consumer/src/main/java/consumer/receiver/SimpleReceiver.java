package consumer.receiver;

import domain.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {
    @RabbitListener(queues = "tutorial-2")
    public void receive(Message message) throws InterruptedException {
        System.out.println(message.id + ":" + message.text);
    }
}
