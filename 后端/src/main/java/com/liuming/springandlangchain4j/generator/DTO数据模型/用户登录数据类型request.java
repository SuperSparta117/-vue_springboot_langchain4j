package com.liuming.springandlangchain4j.generator.DTO数据模型;
import java.io.Serializable;

import lombok.Data;

@Data
public class 用户登录数据类型request implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}
