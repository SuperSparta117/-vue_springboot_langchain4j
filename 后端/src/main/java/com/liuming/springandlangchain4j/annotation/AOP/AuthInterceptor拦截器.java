package com.liuming.springandlangchain4j.annotation.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liuming.springandlangchain4j.annotation.AuthCheck;
import com.liuming.springandlangchain4j.exception.业务异常;
import com.liuming.springandlangchain4j.exception.错误代码;
import com.liuming.springandlangchain4j.generator.DTO数据模型.枚举.用户角色枚举;
import com.liuming.springandlangchain4j.generator.entity.User;
import com.liuming.springandlangchain4j.generator.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

 
@Aspect
@Component
public class AuthInterceptor拦截器 {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     * *@Around注解表示在方法执行前后都进行拦截，即环绕通知。@annotation(authCheck)表示拦截所有被@AuthCheck注解标记的方法，并将注解对象传入如下方法参数中。
     * @param joinPoint 切入点
     * @param authCheck 权限校验注解
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        用户角色枚举 注解强制的mustRole = 用户角色枚举.getEnumByValue(authCheck.mustRole());
        // 不需要权限，放行
        if (注解强制的mustRole == null) {
            return joinPoint.proceed();
        }
        // 以下为：必须有该权限才通过
        // 获取当前用户具有的权限
        用户角色枚举 用户的role = 用户角色枚举.getEnumByValue(loginUser.getUserRole());
        // 没有权限，拒绝
        if (用户的role == null) {
            throw new 业务异常(错误代码.NO_AUTH_ERROR,"用户没有AuthCheck注解所要求的权限，你现在的权限是：" + loginUser.getUserRole()+"，需要的权限是：" + authCheck.mustRole());
        }
        // 要求必须有管理员权限，但用户没有管理员权限，拒绝
        if (用户角色枚举.ADMIN.equals(注解强制的mustRole) && !用户角色枚举.ADMIN.equals(用户的role)) {
            throw new 业务异常(错误代码.NO_AUTH_ERROR,"用户没有AuthCheck注解所要求的权限，你现在的权限是：" + loginUser.getUserRole()+"，需要的权限是：" + authCheck.mustRole());
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
