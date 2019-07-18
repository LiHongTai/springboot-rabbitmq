package github.roger.component;

import github.roger.producer.AMQPProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RabbitMqProducerController {

    @Autowired
    private AMQPProducer amqpProducer;

    @RequestMapping("/send/{exchange}/{routingKey}")
    public void sendMessage(@PathVariable String exchange,@PathVariable String routingKey){
        Map<String,Object> msgProps = new HashMap<>();
        msgProps.put("token","Roger");
        amqpProducer.sendMessage(exchange,routingKey,null,msgProps);
    }

}
