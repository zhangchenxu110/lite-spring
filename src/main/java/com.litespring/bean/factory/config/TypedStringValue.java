package com.litespring.bean.factory.config;

/**
 * value型参数
 *
 * @author 张晨旭
 * @DATE 2018/8/27
 */
public class TypedStringValue {
    private String value;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
