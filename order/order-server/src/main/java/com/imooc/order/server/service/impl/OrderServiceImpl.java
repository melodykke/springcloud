package com.imooc.order.server.service.impl;

import com.imooc.order.server.DTO.OrderDTO;
import com.imooc.order.server.enums.OrderEnum;
import com.imooc.order.server.enums.PayEnum;
import com.imooc.order.server.enums.ResultEnum;
import com.imooc.order.server.exception.OrderException;
import com.imooc.order.server.mapper.OrderDetailMapper;
import com.imooc.order.server.mapper.OrderMasterMapper;
import com.imooc.order.server.model.OrderDetail;
import com.imooc.order.server.model.OrderMaster;
import com.imooc.order.server.service.OrderService;
import com.imooc.product.client.ProductClient;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductClient productClient;

    @Override
    public String create(OrderDTO orderDTO) {
        String orderId = UUID.randomUUID().toString().replaceAll("-", "");
        // 查询商品信息（调用商品服务）
        List<String> productIdList = orderDTO.getOrderDetails().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfoOutputs = productClient.listForOrder(productIdList);
        // 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (ProductInfoOutput productInfoOutput : productInfoOutputs) {
            for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
                if (productInfoOutput.getProductId().equals(orderDetail.getProductId())) {
                    // 单价 * 数量
                    orderAmount = orderAmount.add(productInfoOutput.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity())));
                    BeanUtils.copyProperties(productInfoOutput, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(UUID.randomUUID().toString().replaceAll("-", ""));
                    // 订单详情入库
                    orderDetailMapper.insert(orderDetail);
                }
            }
        }

        // 扣库存(调用商品服务）
        List<DecreaseStockInput> decreaseStockInputs = orderDTO.getOrderDetails().stream()
                .map(n -> new DecreaseStockInput(n.getProductId(), n.getProductQuantity())).collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputs);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderEnum.NEW.getCode().byteValue());
        orderMaster.setPayStatus(PayEnum.WAIT.getCode().byteValue());

        int result = orderMasterMapper.insert(orderMaster);

        if (result == 1) {
            return orderDTO.getOrderId();
        } else {
            log.error("【订单服务】创建订单失败！ 返回result={}", result);
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public OrderDTO orderFinish(String orderId) {

        // 1. 根据orderId先查询订单
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (orderMaster == null) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2. 判断订单状态
        if (orderMaster.getOrderStatus() != OrderEnum.NEW.getCode().byteValue()) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderEnum.FINISHED.getCode().byteValue());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

        // 查询订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.findByOrderId(orderId);

        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetails);

        return orderDTO;

    }

}
