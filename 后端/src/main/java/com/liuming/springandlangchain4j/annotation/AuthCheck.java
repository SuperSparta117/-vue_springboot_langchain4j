package com.liuming.springandlangchain4j.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编写权限校验 AOP，采用环绕通知，
 * 在 打上该注解的方法 执行前后进行一些额外的操作，比如校验权限。
 * 这里ElementType是自定义的注解的作用目标，METHOD表示该注解只能作用于方法上。
 * RetentionPolicy是自定义注解的保留策略，RUNTIME表示该注解会在运行时保留，可以通过反射获取到该注解的信息。
 * 例如：@AuthCheck(mustRole = "admin")，表示该方法需要管理员权限才能访问。
 * 通过AOP的环绕通知，在方法执行前获取当前登录用户的权限信息，并与注解中指定的权限角色进行比较，如果用户没有足够的权限，则抛出异常拒绝访问；如果用户有足够的权限，则继续执行方法。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 注解参数键值对，必须有某个角色
     */
    String mustRole() default "";
}
