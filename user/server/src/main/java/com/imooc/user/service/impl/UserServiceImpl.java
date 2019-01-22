package com.imooc.user.service.impl;

import com.imooc.user.mapper.UserInfoMapper;
import com.imooc.user.model.UserInfo;
import com.imooc.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoMapper.findByOpenid(openid);
    }

}
