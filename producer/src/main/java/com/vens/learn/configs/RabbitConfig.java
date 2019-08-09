package com.vens.learn.configs;


import com.vens.learn.constant.StaffConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    /**
     * 业务队列
     */
    @Bean
    public Queue bizQueue() {
        Queue queue = new Queue(StaffConstant.BIZ_QUEUE_NAME, false);
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", StaffConstant.DEAD_LETTER_EXCHANGE);
        params.put("x-message-ttl", StaffConstant.TTL);
        //durable是否持久化
        return QueueBuilder.durable(StaffConstant.BIZ_QUEUE_NAME).withArguments(params).build();
    }

    /**
     * 死信队列
     *
     * @return
     */
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(StaffConstant.DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(StaffConstant.DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(StaffConstant.BIZ_EXCHANGE);
    }

    @Bean
    public Binding bizBinding() {
        return BindingBuilder.bind(bizQueue()).to(directExchange()).with(StaffConstant.BIZ_ROUTE_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(topicExchange()).with(StaffConstant.DEAD_LETTER_ROUTE_KEY);
    }


}
