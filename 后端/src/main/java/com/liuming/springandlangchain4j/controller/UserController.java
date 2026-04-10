package com.liuming.springandlangchain4j.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuming.springandlangchain4j.annotation.AuthCheck;
import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.统一条件异常throwUtil;
import com.liuming.springandlangchain4j.exception.错误代码;
import com.liuming.springandlangchain4j.exception.前后端异常统一.前端发送数据格式删除工具;
import com.liuming.springandlangchain4j.exception.前后端异常统一.前端接收数据格式baseresponse;
import com.liuming.springandlangchain4j.exception.前后端异常统一.前端接收数据统一异常出口result;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserAddRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserQueryRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserUpdateRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserVO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户注册数据类型request;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录数据类型request;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录脱敏数据类型VO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户常量;
import com.liuming.springandlangchain4j.generator.entity.User;
import com.liuming.springandlangchain4j.generator.service.UserService;
import com.mybatisflex.core.paginate.Page;

import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

 
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    //由 MyBatis-Flex Generator生成的代码，
    /**
     * 保存用户。
     *
     * @param user 用户
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 根据主键删除用户。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return userService.removeById(id);
    }

    /**
     * 根据主键更新用户。
     *
     * @param user 用户
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }

    /**
     * 查询所有用户。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 根据主键获取用户。
     *
     * @param id 用户主键
     * @return 用户详情
     */
    @GetMapping("getInfo/{id}")
    public User getInfo(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * 分页查询用户。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }

    







    
    //业务代码
    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return 注册结果
     */
    @SuppressWarnings("null")
    @PostMapping("register")
    public 前端接收数据格式baseresponse<Long> userRegister(@RequestBody 用户注册数据类型request userRegisterRequest) {
        统一条件异常throwUtil.throwIf(userRegisterRequest == null, 错误代码.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return 前端接收数据统一异常出口result.success(result);
    }
    
    @SuppressWarnings("null")
    @PostMapping("/login")
    public 前端接收数据格式baseresponse<用户登录脱敏数据类型VO> userLogin(@RequestBody 用户登录数据类型request userLoginRequest, HttpServletRequest request) {
        统一条件异常throwUtil.throwIf(userLoginRequest == null, 错误代码.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        用户登录脱敏数据类型VO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return 前端接收数据统一异常出口result.success(loginUserVO);
    }


    @GetMapping("/get/login")
    public 前端接收数据格式baseresponse<用户登录脱敏数据类型VO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return 前端接收数据统一异常出口result.success(userService.getLoginUserVO(loginUser));
    }


    @PostMapping("/logout")
    public 前端接收数据格式baseresponse<Boolean> userLogout(HttpServletRequest request) {
        统一条件异常throwUtil.throwIf(request == null, 错误代码.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        return 前端接收数据统一异常出口result.success(result);
    }








    //用户管理的接口
    /**
     * 创建用户
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = 用户常量.ADMIN_ROLE)
    public 前端接收数据格式baseresponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        统一条件异常throwUtil.throwIf(userAddRequest == null, 错误代码.PARAMS_ERROR);
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = userService.getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        统一条件异常throwUtil.throwIf(!result, 错误代码.OPERATION_ERROR);
        return 前端接收数据统一异常出口result.success(user.getId());
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = 用户常量.ADMIN_ROLE)
    public 前端接收数据格式baseresponse<User> getUserById(long id) {
        统一条件异常throwUtil.throwIf(id <= 0, 错误代码.PARAMS_ERROR);
        User user = userService.getById(id);
        统一条件异常throwUtil.throwIf(user == null, 错误代码.NOT_FOUND_ERROR);
        return 前端接收数据统一异常出口result.success(user);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    public 前端接收数据格式baseresponse<UserVO> getUserVOById(long id) {
        前端接收数据格式baseresponse<User> response = getUserById(id);
        User user = response.getData();
        return 前端接收数据统一异常出口result.success(userService.getUserVO(user));
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = 用户常量.ADMIN_ROLE)
    public 前端接收数据格式baseresponse<Boolean> deleteUser(@RequestBody 前端发送数据格式删除工具 deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new 业务异常(错误代码.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return 前端接收数据统一异常出口result.success(b);
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = 用户常量.ADMIN_ROLE)
    public 前端接收数据格式baseresponse <Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new 业务异常(错误代码.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        统一条件异常throwUtil.throwIf(!result, 错误代码.OPERATION_ERROR);
        return 前端接收数据统一异常出口result.success(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryRequest 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = 用户常量.ADMIN_ROLE)
    public 前端接收数据格式baseresponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        统一条件异常throwUtil.throwIf(userQueryRequest == null, 错误代码.PARAMS_ERROR);
        @SuppressWarnings("null")
        long pageNum = userQueryRequest.getPageNum();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(Page.of(pageNum, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        // 数据脱敏
        Page<UserVO> userVOPage = new Page<>(pageNum, pageSize, userPage.getTotalRow());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return 前端接收数据统一异常出口result.success(userVOPage);
    }

}
