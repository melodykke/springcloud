package com.imooc.order.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ClientController {

    /*@Autowired //这是第二种方式的玩法
    private LoadBalancerClient loadBalancerClient;*/

    /*@Autowired
    private RestTemplate restTemplate;*/

   /* @Autowired
    private ProductClient productClient;*/

    @GetMapping("/getProductMsg")
    public String getProductMsg() {

        // 1. 直接使用restTemplate 请求地址写死
       /* RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8081/msg", String.class);*/

        // 2. 第二种方式 使用springcloud loadbalance在服务集群中自主选择一个服务地址(负载均衡）， 然后resttemplate去请求
       /* ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String questUrl = String.format("http://%s:%s/msg", serviceInstance.getHost(), serviceInstance.getPort());
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(questUrl, String.class);*/

        // 3. 第三种方式 用LoadBalanced注解配置RestTemplate类(负载均衡）
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);

        // 4. feign
       /* String response = productClient.productMsg();
        log.info("response={}", response);
        return response;*/
       return null;
    }

    @PostMapping("/getProductInfoByIds")
    public String getProductInfoByIds(@RequestBody List<String> productIds) {
        /*List<ProductInfo> productInfos = productClient.listForOrder(productIds);
        log.info("productInfos={}", productInfos);*/
        return "OK";
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock() {
        // productClient.decreaseStock(Arrays.asList(new CartDTO("157875196366160022", 5)));
        return "ok";
    }
}
