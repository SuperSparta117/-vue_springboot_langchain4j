package com.liuming.springandlangchain4j.generator.DTO数据模型.枚举;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum 用户角色枚举 {

    USER("用户", "user"),
    ADMIN("管理员", "admin");

    private final String text;

    private final String value;

    用户角色枚举(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的value
     * @return 枚举值
     */
    public static 用户角色枚举 getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (用户角色枚举 anEnum : 用户角色枚举.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
