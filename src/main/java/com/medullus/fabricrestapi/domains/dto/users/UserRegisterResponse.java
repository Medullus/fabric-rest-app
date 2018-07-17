package com.medullus.fabricrestapi.domains.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class UserRegisterResponse {


    @JsonIgnore
    private ResponseHeader responseHeader;
    @ApiModelProperty
    private UserRegister userRegister;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public UserRegister getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @Override
    public String toString() {
        return "UserRegisterResponse{" +
                "responseHeader=" + responseHeader +
                ", userRegister=" + userRegister +
                '}';
    }
}
