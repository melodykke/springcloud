package com.imooc.user.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    LOGIN_FAILURE(1, "登陆失败"),
    ROLE_ERROR(2, "角色权限有误")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
