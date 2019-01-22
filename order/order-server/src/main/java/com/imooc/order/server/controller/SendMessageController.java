package com.imooc.order.server.controller;

import com.imooc.order.server.DTO.OrderDTO;
import com.imooc.order.server.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {

   /* @Autowired
    private StreamClient streamClient;*/

   @Autowired
   private StreamClient streamClient;

   /* @GetMapping("/sendMessage")
    public void process() {
        String message = "now " + new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }*/

    /**
     * 发送 orderDTO对象
     */
    @GetMapping("/sendMessage")
    public void process() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        orderDTO.setBuyerName("李兢");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}