package com.liuming.springandlangchain4j.generator.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.错误代码;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserQueryRequest;
import com.liuming.springandlangchain4j.generator.DTO数据模型.UserVO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.用户登录脱敏数据类型VO;
import com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户角色枚举;
import com.liuming.springandlangchain4j.generator.entity.User;
import com.liuming.springandlangchain4j.generator.mapper.UserMapper;
import com.liuming.springandlangchain4j.generator.service.UserService;

import static com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户常量.USER_LOGIN_STATE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
/**
 * 用户 服务层实现。
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new 业务异常(错误代码 .PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2. 检查是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "账号重复");
        }
        // 3. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 4. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(用户角色枚举.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new 业务异常(错误代码.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }
    //我们需要将用户密码加密后进行存储。可以封装一个方法，便于后续复用
     
    @SuppressWarnings("null")
    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "yupi";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }


    // VO 确实只是去掉了 userPassword，其他字段全部原样返回，并没有对任何字段做真正的脱敏处理（比如手机号打码、邮箱隐藏等）。
    @Override
    public 用户登录脱敏数据类型VO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        用户登录脱敏数据类型VO loginUserVO = new 用户登录脱敏数据类型VO();
        //其实就是将 User 类的属性复制到 LoginUservo 中，不存在的字段就被
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public 用户登录脱敏数据类型VO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = getEncryptPassword(userPassword);
        // 查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态，将用户信息作为成员变量 存进 Session对象
        // 如果这步没有Session ID，会自动分配并new对象 。 
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 4. 获得脱敏后的用户信息
        return this.getLoginUserVO(user);
    }

    // 在 UserServicelmpl 中增加实现代码，此处为了保证获取到的数据始终是最新的，先从 Session中获取登录用户的 id，然后从数据库中查询最新的结果。代码如下:
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new 业务异常(错误代码.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接返回上述结果）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new 业务异常(错误代码.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }


    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new 业务异常(错误代码.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id", id)
                .eq("userRole", userRole)
                .like("userAccount", userAccount)
                .like("userName", userName)
                .like("userProfile", userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }

}
