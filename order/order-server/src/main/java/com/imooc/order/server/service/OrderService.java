package com.imooc.order.server.service;

import com.imooc.order.server.DTO.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return orderId
     */
    String create(OrderDTO orderDTO);
}
