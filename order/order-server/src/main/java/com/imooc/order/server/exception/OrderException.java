package com.imooc.order.server.exception;


import com.imooc.order.server.enums.ResultEnum;

public class OrderException extends RuntimeException {
    private Integer code;

    public OrderException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
