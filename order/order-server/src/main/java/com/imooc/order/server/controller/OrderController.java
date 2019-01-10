package com.imooc.order.server.controller;

import com.google.common.collect.Maps;
import com.imooc.order.server.DTO.OrderDTO;
import com.imooc.order.server.VO.ResultVO;
import com.imooc.order.server.converter.OrderForm2OrderDTOConverter;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.order.server.form.OrderForm;
import com.imooc.order.server.service.OrderService;
import com.imooc.order.server.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】 参数不正确， orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("【创建订单】购物车信息为空！");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        String orderId = orderService.create(orderDTO);

        Map<String, String> map = Maps.newHashMap();
        map.put("orderId", orderId);

        return ResultVOUtil.success(map);
    }

}
