
package com.medullus.fabricrestapi.domains.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RequestHeader{

    @ApiModelProperty(example = "AtlasAmericas")
    private String caller;
    @ApiModelProperty(example = "Org1")
    private String org;

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                '}';
    }
}