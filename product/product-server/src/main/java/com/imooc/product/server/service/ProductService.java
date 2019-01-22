package com.imooc.product.server.service;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.server.model.ProductInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param productIds
     * @return
     */
    List<ProductInfo> findList(List<String> productIds);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);



}
