package com.imooc.user.service;

import com.imooc.user.model.UserInfo;

public interface UserService {

    UserInfo findByOpenid(String openid);
}
