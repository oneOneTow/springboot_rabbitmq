package com.vens.learn.configs;



import com.vens.learn.constant.StaffConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Queue getQueue(){
        Queue queue = new Queue(StaffConstant.QUEUE_NAME,false);
        return queue;
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(StaffConstant.EXCHANGE);
    }
    @Bean
    Binding bindingExchangeA(Queue Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(Queue).to(fanoutExchange);
    }



}
