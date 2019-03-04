# springboot_rabbitmq
## 1.自定义的消息体需要实现Serializable接口
不然有Caused by: org.springframework.amqp.AmqpException: No method found for class [B
## 但是当需要序列化的对象中包含有LocalDateTime，使用默认的jdk序列化同样会报上述错误，这时候需要使用Jackson2JsonMessageConverter
` @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    } `将这段代码贴在配置文件中
## 2.端口号是5672，admin的端口号是15672
## 3.ack机制
> producer和consumer都有ack,producer是确定方法是否发送到queue,consumer ack是用户手动ack
consumer配置文件
spring:
  application:
    name: consumer
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    listener:
      simple:
        acknowledge-mode: manual
producer配置文件
spring:
  application:
    name: producer
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    publisher-confirms: true
    publisher-returns: true
    template:
      mandatory: true
      
reciver

public void process(@Payload PersonMessage message, @Headers Map<String,Object> headers, Channel channel){
        System.out.println(message.toString());
        try {
            channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG),false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
        
