package com.imooc.order.server;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 模拟发送端 发送mq消息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqSenderTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {

        amqpTemplate.convertAndSend("myQueue", "now " + new Date());

    }

    @Test
    public void sendComputerOrder() {

        amqpTemplate.convertAndSend("myOrder", "computer", "now computer" + new Date());

    }

    @Test
    public void sendFruitOrder() {

        amqpTemplate.convertAndSend("myOrder", "fruit", "now fruit" + new Date());

    }

}
