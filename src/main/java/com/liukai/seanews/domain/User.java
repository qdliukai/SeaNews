package com.liukai.seanews.domain;

public class User {
    private Integer userId ;
    private String userName ;
    private String userPassword;
    private String userCollect;
    private String userModel;
    private String userModelUpdateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCollect() {
        return userCollect;
    }

    public void setUserCollect(String userCollect) {
        this.userCollect = userCollect;
    }

    public String getUserModel() {
        return userModel;
    }

    public void setUserModel(String userModel) {
        this.userModel = userModel;
    }

    public String getUserModelUpdateTime() {
        return userModelUpdateTime;
    }

    public void setUserModelUpdateTime(String userModelUpdateTime) {
        this.userModelUpdateTime = userModelUpdateTime;
    }
}
