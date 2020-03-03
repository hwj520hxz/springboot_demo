package com.boss.demo.entity;

import java.io.Serializable;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
public class EventDTO implements Serializable {
    // 列字段1
    private String name;
    // 列字段2
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
