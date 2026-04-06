package com.liuming.springandlangchain4j.exception.前后端异常统一;

import java.io.Serializable;

import com.liuming.springandlangchain4j.exception.错误代码;

import lombok.Data;

// 一般情况下，每个后端接口都要返回调用码、数据、调用信息等，前端可以根据这些信息进行相应的处理。
// 我们可以封装统一的响应结果类，便于前端统一获取这些信息。
//前段请求成功时，前端会得到：
// {
//   "code": 0,
//   "data": {
//     "id": 1,
//     "name": "Tom"
//   },
//   "message": "ok"
// }
@Data
public class 前端接收数据格式response<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public 前端接收数据格式response(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public 前端接收数据格式response(int code, T data) {
        this(code, data, "");
    }

    public 前端接收数据格式response(错误代码 errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
