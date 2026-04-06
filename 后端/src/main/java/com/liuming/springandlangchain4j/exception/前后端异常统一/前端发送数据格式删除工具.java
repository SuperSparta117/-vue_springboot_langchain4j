package com.liuming.springandlangchain4j.exception.前后端异常统一;
import java.io.Serializable;

import lombok.Data;

@Data
public class 前端发送数据格式删除工具 implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
