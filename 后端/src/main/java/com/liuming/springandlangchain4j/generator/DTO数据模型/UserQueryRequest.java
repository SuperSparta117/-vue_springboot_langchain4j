package com.liuming.springandlangchain4j.generator.DTO数据模型;

import java.io.Serializable;
import com.liuming.springandlangchain4j.exception.前后端异常统一.前端发送数据格式pagerequest;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends 前端发送数据格式pagerequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
