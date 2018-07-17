package com.medullus.fabricrestapi.domains.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserRegisterRequest {

    @ApiModelProperty
    private RequestHeader requestHeader;

    @ApiModelProperty(value = "username and pw registration")
    private UserRegister userRegister;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "requestHeader=" + requestHeader +
                ", userRegister=" + userRegister +
                '}';
    }
}
