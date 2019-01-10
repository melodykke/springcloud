package com.imooc.order.server.enums;

import lombok.Getter;

@Getter
public enum PayEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String msg;

    PayEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
