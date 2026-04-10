package com.liuming.springandlangchain4j.generator.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import com.liuming.springandlangchain4j.generator.DTO数据模型.UserQueryRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserVO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录脱敏数据类型VO;
import com.liuming.springandlangchain4j.generator.entity.User;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    String getEncryptPassword(String userPassword);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    用户登录脱敏数据类型VO getLoginUserVO(User user);

    /**
     * 用户登录，其实依赖的是在request关联的session对象中存一个键值对完成的。
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
        用户登录脱敏数据类型VO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);


    UserVO getUserVO(User user);


    List<UserVO> getUserVOList(List<User> userList);


    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);



}


