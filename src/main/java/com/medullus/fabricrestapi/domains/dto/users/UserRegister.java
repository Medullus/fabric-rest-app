package com.medullus.fabricrestapi.domains.dto.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserRegister {

    @ApiModelProperty(example = "AtlasAmerica")
    private String userName;
    @ApiModelProperty(example = "defaultpw")
    private String secret;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "UserRegister{" +
                "userName='" + userName + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
