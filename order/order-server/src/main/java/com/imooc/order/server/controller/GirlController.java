package com.imooc.order.server.controller;

import com.imooc.order.server.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/girl")
public class GirlController {

    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/name")
    public String getName() {
        return girlConfig.getName();
    }

}
