package com.imooc.product.server.exception;

import com.imooc.product.server.enums.ResultEnum;
import lombok.Data;

@Data
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
