package consumer.receiver;

import domain.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {
    @RabbitListener(queues = "tutorial-2")
    public void receive(Student student) throws InterruptedException {
        System.out.println(student.id);
        System.out.println(student.name);
        System.out.println(student.score);
        Thread.sleep(100);
    }
}
