package com.imooc.product.server.service.impl;

import com.imooc.product.server.DTO.CartDTO;
import com.imooc.product.server.enums.ProductStatusEnum;
import com.imooc.product.server.enums.ResultEnum;
import com.imooc.product.server.exception.ProductException;
import com.imooc.product.server.mapper.ProductInfoMapper;
import com.imooc.product.server.model.ProductInfo;
import com.imooc.product.server.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIds) {
        return productInfoMapper.findByProductIds(productIds);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOs) {
        cartDTOs.forEach(n -> {
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
        });
    }
}
