package com.common;

/**
 * 待优化，尽量要服务器和客户端分离
 */
public class User implements java.io.Serializable{

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
