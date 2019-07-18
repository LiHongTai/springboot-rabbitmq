package github.roger.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static github.roger.constant.RabbitMqConstant.*;

@Configuration
public class RabbitMqConfig {

    /**
     * 队列名字
     * durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的队列
     * auto-delete    表示消息队列没有在使用时将被自动删除 默认是false
     * exclusive      表示该消息队列是否只在当前connection生效,默认是false
     */
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE,true,false,false);
    }
    /**
     * 直接交换机
     * durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的队列
     * auto-delete    表示消息队列没有在使用时将被自动删除 默认是false
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE,true,false);
    }

    /**
     * 将direct队列和直接交换机进行绑定
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_KEY);
    }

    @Bean
    public Queue topicQueue(){
        return new Queue(TOPIC_QUEUE,true,false,false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindTopic(){
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC_KEY);
    }


    @Bean
    public Queue fanoutQueue1(){
        return new Queue(FANOUT_QUEUE + "1",true,false,false);
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue(FANOUT_QUEUE + "2",true,false,false);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindingFanout1(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanout2(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }
}
