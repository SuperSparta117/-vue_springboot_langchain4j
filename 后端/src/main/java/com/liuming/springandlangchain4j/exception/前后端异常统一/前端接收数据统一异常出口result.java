package com.liuming.springandlangchain4j.exception.前后端异常统一;

import com.liuming.springandlangchain4j.exception.错误代码;

public class 前端接收数据统一异常出口result {

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应
     */
    public static <T> 前端接收数据格式baseresponse<T> success(T data) {
        return new 前端接收数据格式baseresponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static 前端接收数据格式baseresponse<?> error(错误代码 errorCode) {
        return new 前端接收数据格式baseresponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 响应
     */
    public static 前端接收数据格式baseresponse<?> error(int code, String message) {
        return new 前端接收数据格式baseresponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static 前端接收数据格式baseresponse<?> error(错误代码 errorCode, String message) {
        return new 前端接收数据格式baseresponse<>(errorCode.getCode(), null, message);
    }
}
