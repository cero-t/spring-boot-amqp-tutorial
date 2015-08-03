package consumer.receiver;

import domain.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {
    @RabbitListener(queues = "tutorial-4-1")
    public void receive1(Student student) throws InterruptedException {
        System.out.println("4-1:" + student.id + " - " + student.score);
    }

    @RabbitListener(queues = "tutorial-4-2")
    public void receive2(Student student) throws InterruptedException {
        System.out.println("4-2:" + student.id + " - " + student.score);
    }
}
