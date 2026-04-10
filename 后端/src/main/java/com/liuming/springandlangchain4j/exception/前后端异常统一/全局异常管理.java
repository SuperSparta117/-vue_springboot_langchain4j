package com.liuming.springandlangchain4j.exception.前后端异常统一;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.错误代码;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

// 这段代码是在做全局异常处理。
// 它的作用是:
// 只要后端代码抛了异常，就由它统一接住，再统一返回给前端。
// 不是让错误直接爆出去。

// 注意!由于本项目使用的 Spring Boot 版本 >= 3.4、并且是 OpenAP13 版本的 Knife4j，这会导致 @RestcontrollerAdvice 注解不兼容，所以必须给这个类加上 @Hidden 注解，不被 Swagger加载。虽然网上也有其他的解决方案，但这种方法是最直接有效的。
@Hidden
@RestControllerAdvice
@Slf4j
public class 全局异常管理 {

    @ExceptionHandler(业务异常.class)
    public 前端接收数据格式baseresponse<?> businessExceptionHandler(业务异常 e) {
        log.error("BusinessException", e);
        return 前端接收数据统一异常出口result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public 前端接收数据格式baseresponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return 前端接收数据统一异常出口result.error(错误代码.SYSTEM_ERROR, "系统错误");
    }
}
