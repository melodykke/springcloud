package com.imooc.product.server.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;
}
