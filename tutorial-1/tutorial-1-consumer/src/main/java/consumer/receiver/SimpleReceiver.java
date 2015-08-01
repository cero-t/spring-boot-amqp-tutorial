package consumer.receiver;

import domain.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {
    @RabbitListener(queues = "tutorial-1")
    public void receive(Student student) {
        System.out.println(student.id);
        System.out.println(student.name);
        System.out.println(student.score);
    }
}
