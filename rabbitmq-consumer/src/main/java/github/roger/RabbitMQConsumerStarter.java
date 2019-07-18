package github.roger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMQConsumerStarter {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQConsumerStarter.class,args);
    }
}
