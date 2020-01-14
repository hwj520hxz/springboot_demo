package com.boss.demo.entity;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @ClassName 类名：User
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：15:43
 */
public class User {
    private String id;
    private String name;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
