package com.medullus.fabricrestapi.domains.dto.users;

public class UserServicePojo {

    private UserRegister userRegister;
    private String caller;

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @Override
    public String toString() {
        return "UserServicePojo{" +
                "userRegister=" + userRegister +
                ", caller='" + caller + '\'' +
                '}';
    }
}
