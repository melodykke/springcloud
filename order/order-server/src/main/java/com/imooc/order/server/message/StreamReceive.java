package com.imooc.order.server.message;

import com.imooc.order.server.DTO.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceive {

    /**
     * 接收orderDTO对象 消息
     * @param message
     */
/*    @StreamListener(value = StreamClient.INPUT2)
    @SendTo(StreamClient.INPUT)
    public String process(OrderDTO message) {
        log.info("StreamReceiver: {}", message);
        return "received.";
    }*/

  /*  @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String process2(String message) {
        log.info("Received: {}", message);
        return message;
    }*/

}

