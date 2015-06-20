package consumer.sender;

import domain.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() {
        Student student = new Student();
        student.id = 1;
        student.name = "TEST";
        student.score = 100;

        rabbitTemplate.convertAndSend("spring-boot", student);
    }
}
