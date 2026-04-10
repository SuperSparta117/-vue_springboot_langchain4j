package com.liuming.springandlangchain4j.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuming.springandlangchain4j.exception.前后端异常统一.前端接收数据格式baseresponse;
import com.liuming.springandlangchain4j.exception.前后端异常统一.前端接收数据统一异常出口result;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public 前端接收数据格式baseresponse<String> healthCheck() {
        return 前端接收数据统一异常出口result.success( "ok");
    }
}
 
