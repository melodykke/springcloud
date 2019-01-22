package com.imooc.order.server.message;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductInfoReceiver {

    public static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductInfoOutput.class);
        List<ProductInfoOutput> productInfoOutputList = Lists.newArrayList();
        try {
            productInfoOutputList = objectMapper.readValue(message, javaType);
            log.info("从 【{}】 接受到消息：{}", "productInfo", productInfoOutputList);
        } catch (IOException e) {
            log.error("订单服务接受扣库存消息体转化失败！");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        if (productInfoOutputList.size() == 0) {
            log.error("订单服务接受扣库存消息体转化失败！");
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        productInfoOutputList.forEach(e -> {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, e.getProductId()),
                    String.valueOf(e.getProductStock()));
        });

    }


}
