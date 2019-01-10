package com.imooc.order.server.enums;

import lombok.Getter;

@Getter
public enum OrderEnum {

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消订单"),

    ;

    private Integer code;

    private String msg;

    OrderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
