package github.roger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMQProducerStarter {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProducerStarter.class,args);
    }
}
