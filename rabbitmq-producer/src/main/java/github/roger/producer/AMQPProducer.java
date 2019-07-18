package github.roger.producer;

import github.roger.callback.MsgConfirmCallBack;
import github.roger.callback.MsgReturnCallBack;
import github.roger.constant.RabbitMqConstant;
import github.roger.utils.MsgIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class AMQPProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgConfirmCallBack msgConfirmCallBack;

    @Autowired
    private MsgReturnCallBack msgReturnCallBack;

    /**
     * rabbitTemplate.send(message)---- 发消息
     *      参数对象为org.springframework.amqp.core.Message
     * rabbitTemplate.convertAndSend(message)---- 转换并发送消息;
     *      1）将参数对象转换为org.springframework.amqp.core.Message后发送
     *      2）消费者不能有返回值
     * rabbitTemplate.convertSendAndReceive(message)---- 转换并发送消息
     *      1）将参数对象转换为org.springframework.amqp.core.Message后发送
     *      2）消费者可以有返回值
     *      2）等待消费者返回响应消息，也可以不等待
     */
    public void sendMessage(String exchange,String routingKey,Object msgContext, Map<String,Object> msgProps){
        //设置消息确认回调函数
        rabbitTemplate.setConfirmCallback(msgConfirmCallBack);
        //设置消息不可达处理函数
        rabbitTemplate.setReturnCallback(msgReturnCallBack);
        //设置消息头
        MessageProperties messageProperties = new MessageProperties();
        for(Map.Entry<String,Object> msgPropEntry:msgProps.entrySet()){
            messageProperties.setHeader(msgPropEntry.getKey(),msgPropEntry.getValue());
        }
        //生成消息的唯一ID
        String messageId = MsgIdGenerator.createRandomId();

        //用于生产者确认消息时返回确认
        CorrelationData correlationData = new CorrelationData(messageId);
        //创建消息体
        String body = "将消息Id为" +messageId+ "的消息发送到交换机名称为" + exchange + ",路由键为" +  routingKey +"的消息";
        //发送消息的类型是 byte[]
        // @RabbitHandler标记的方法的参数类型 要和该消息体的类型一致
        Message message = MessageBuilder.withBody(body.getBytes()).andProperties(messageProperties).build();
        log.info("发送消息的内容为：" + body);

        //通过交换机发送消息
        if(RabbitMqConstant.FANOUT_EXCHANGE.equals(exchange)){
            //直接发送String类型的消息
            rabbitTemplate.convertAndSend(exchange,"",body,correlationData);
            return ;
        }
        //发送 byte[] 类型的数据
        rabbitTemplate.convertAndSend(exchange,routingKey,message,correlationData);

    }



}
