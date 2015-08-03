package producer.sender;

import domain.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class SimpleSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() {
        Message message = new Message();
        IntStream.range(0, 10)
                .forEach(i -> {
                    message.id = i;
                    message.text = "Hello - " + i;

                    rabbitTemplate.convertAndSend("tutorial-2", message);
                });
    }
}
