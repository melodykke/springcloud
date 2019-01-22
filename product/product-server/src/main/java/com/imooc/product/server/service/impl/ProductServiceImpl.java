package com.imooc.product.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.server.enums.ProductStatusEnum;
import com.imooc.product.server.enums.ResultEnum;
import com.imooc.product.server.exception.ProductException;
import com.imooc.product.server.mapper.ProductInfoMapper;
import com.imooc.product.server.model.ProductInfo;
import com.imooc.product.server.service.ProductService;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIds) {
        return productInfoMapper.findByProductIds(productIds);
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);

        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());

        // 库存改动成功后，发送mq消息
        try {
            String productInfoOutputListStr = new ObjectMapper().writeValueAsString(productInfoOutputList);
            amqpTemplate.convertAndSend("productInfo", productInfoOutputListStr);
        } catch (JsonProcessingException e) {
            log.error("减库存失败，商品信息转化出错！");
            throw new ProductException(ResultEnum.JSON_CONVERT_ERROR);
        }


    }
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = Lists.newArrayList();
        decreaseStockInputList.forEach(n -> {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(n.getProductId());
            if (productInfo == null) {
                log.error("减库存失败，查无对应商品！");
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer remain = productInfo.getProductStock() - n.getProductQuantity();
            if (remain < 0) {
                log.error("减库存失败，库存不足！");
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(remain);
            productInfoMapper.updateByPrimaryKey(productInfo);
            productInfoList.add(productInfo);
        });
        return productInfoList;
    }
}
