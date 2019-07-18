package github.roger.component;

import com.rabbitmq.client.Channel;
import github.roger.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//监听队列
@RabbitListener(queues = RabbitMqConstant.TOPIC_QUEUE)
@Slf4j
public class TopicExchangeConsumer {

    //处理消息方法，方法参数类型
    @RabbitHandler
    public void execute(byte[] body,Channel channel, Message message) throws IOException{
        try {
            log.info(message.getBody() + " 消息处理完成");
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.error("MQ消息处理异常，消息ID：{}，消息体:{}", message.getMessageProperties().getCorrelationId(), message.getBody(), e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
