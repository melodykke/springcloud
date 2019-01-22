package com.imooc.order.server.service;

import com.imooc.order.server.DTO.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return orderId
     */
    String create(OrderDTO orderDTO);

    /**
     * 完结订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDTO orderFinish(String orderId);
}
