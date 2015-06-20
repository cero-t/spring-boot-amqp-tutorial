package consumer;

import consumer.sender.SimpleSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
        SimpleSender sender = context.getBean(SimpleSender.class);
        sender.send();
        context.close();
    }
}
