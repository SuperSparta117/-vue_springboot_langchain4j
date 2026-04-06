package com.liuming.springandlangchain4j.exception;

import lombok.Getter;

// 一般不建议直接抛出 Java 内置的 RuntimeException，而是自定义一个业务异常，和内置的异常类区分开，便于定制化输出错误信息:
@Getter
public class 业务异常 extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public 业务异常(int code, String message) {
        super(message);
        this.code = code;
    }

    public  业务异常(错误代码 errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public 业务异常(错误代码 errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}


