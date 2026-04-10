package com.liuming.springandlangchain4j.exception;

// 4. 如果没有，会发生什么

// 不是说“程序会跑不了”，而是会出现这些问题。

// 4.1 代码啰嗦

// 每次都要写：

// if (condition) {
//     throw new BusinessException(...);
// }

// 大量重复。

// 4.2 风格不统一

// 有的人抛 RuntimeException，有的人抛 BusinessException，有的人错误码不传，有的人 message 写得很随意。

// 最后全局异常处理很难规范。

// 4.3 后续维护成本高

// 如果后面你们要求：

// 所有业务异常必须打印 warn 日志
// 所有异常都要带统一前缀
// 所有异常都要附带错误码说明

// 那你得全项目慢慢改。

// 如果已经统一走 ThrowUtils，只要改工具类。

// 4.4 容易漏掉细节

// 比如有人写：

// if (obj == null) {
//     throw new RuntimeException("对象为空");
// }

// 这就有问题：

// 异常类型不统一
// 没有错误码
// 前端不好识别
// 全局异常处理可能只能按 500 处理

// 而你本来想表达的是业务参数错误，应该返回类似：

// new BusinessException(ErrorCode.PARAMS_ERROR, "对象为空")

// 工具类可以减少这种低级不一致。

public class 统一条件异常throwUtil {

    /**
     * 条件成立则抛异常
     *
     * @param condition        条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition, 错误代码 errorCode) {
        throwIf(condition, new 业务异常(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void throwIf(boolean condition, 错误代码 errorCode, String message) {
        throwIf(condition, new 业务异常(errorCode, message));
    }
}
