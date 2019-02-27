# springboot_rabbitmq
## 1.自定义的消息体需要实现Serializable接口
不然有Caused by: org.springframework.amqp.AmqpException: No method found for class [B
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
        
