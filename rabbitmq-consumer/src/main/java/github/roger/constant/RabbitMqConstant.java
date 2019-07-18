package github.roger.constant;

public class RabbitMqConstant {
    /**
     * 消息交换机的名字 EXCHANGE
     * */
    public static final String DIRECT_EXCHANGE = "DirectExchange";

    public static final String TOPIC_EXCHANGE = "TopicExchange";

    public static final String FANOUT_EXCHANGE ="FanoutExchange" ;

    public static final String HEADERS_EXCHANGE ="HeadersExchange" ;

    /**
     * 队列的名字 Queue
     * */
    public static final String DIRECT_QUEUE = "DirectQueue";

    public static final String TOPIC_QUEUE = "TopicQueue";

    public static final String FANOUT_QUEUE = "FanoutQueue";

    public static final String HEADERS_QUEUE = "HeadersQueue";

    /**
     * 路由键 routing key
     * */
    public static final String DIRECT_KEY = "DirectKey";

    public static final String TOPIC_KEY = "Topic.#";

}
