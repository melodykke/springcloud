package com.imooc.order.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接受mq的消息
 */
@Slf4j
@Component
public class MqRecevier {

    // 1.@RabbitListener(queues = "myQueue") 直接监听预先建立好的mq
    // 2.@RabbitListener(queuesToDeclare = {@Queue(value = "myQueue1"), @Queue(value = "myQueue", durable = "false")}) 自动建立队列
    // 3.自动创建交换机和队列的关系
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")))
    public void process(String message) {

        log.info("MqReceiver: {}", message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerOrder"),
            key = "computer",
            exchange = @Exchange("myOrder")))
    public void processComputer(String message) {

        log.info("MqComputerReceiver: {}", message);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            key = "fruit",
            exchange = @Exchange("myOrder")))
    public void processFruit(String message) {

        log.info("MqFruitReceiver: {}", message);

    }*/
}
