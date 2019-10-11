package com.vens.learn.mq;


import com.rabbitmq.client.Channel;
import com.vens.learn.constant.StaffConstant;
import com.vens.learn.message.PersonMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = StaffConstant.BIZ_QUEUE_NAME)
public class Receiver {
    @RabbitHandler
    public void process(@Payload PersonMessage message, @Headers Map<String,Object> headers, Channel channel){
        System.out.println(message.toString());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
