package com.vens.learn.configs;


import com.vens.learn.constant.StaffConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public RabbitTemplate template(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

            }
        });
        rabbitTemplate.convertAndSend();
    }


    /**
     * 死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        Queue queue = new Queue(StaffConstant.BIZ_QUEUE_NAME, false);
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", StaffConstant.DEAD_LETTER_EXCHANGE);
        params.put("x-message-ttl", StaffConstant.TTL);
        //durable是否持久化
        return QueueBuilder.durable(StaffConstant.BIZ_QUEUE_NAME).withArguments(params).build();
    }

    /**
     * 业务队列
     */
    @Bean
    public Queue expireQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-expires", 10000);
        return QueueBuilder.durable(StaffConstant.BIZ_QUEUE_NAME).withArguments(params).build();
    }

    /**
     * 死信队列
     *
     * @return
     */
    public Queue bizQueue() {
        return QueueBuilder.durable(StaffConstant.DEAD_LETTER_QUEUE_NAME).build();
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(StaffConstant.DEAD_LETTER_EXCHANGE);
    }

    /**
     * 重置RoutingKey死信交换机
     * @return
     */
    @Bean
    public TopicExchange resetRoutingKeyExchange() {
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-dead-letter-routing-key","reset.routing.key");
        return new TopicExchange(StaffConstant.DEAD_LETTER_EXCHANGE,true,false,arguments);
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

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setExposeListenerChannel(true);
        container.setQueues(deadLetterQueue());
        container.setConcurrentConsumers(2);
        container.setMaxConcurrentConsumers(3);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(10);
        return container;
    }

}
