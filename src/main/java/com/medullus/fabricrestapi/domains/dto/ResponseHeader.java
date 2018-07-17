package com.medullus.fabricrestapi.domains.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResponseHeader{

    @ApiModelProperty(example = "Found transaction")
    private String message;
    @ApiModelProperty(example = "ba599ba599db0b0e8db0baba599db0b0e899db0b0e8b0e8")
    private String txID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTxID() {
        return txID;
    }

    public void setTxID(String txID) {
        this.txID = txID;
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "message='" + message + '\'' +
                ", txID='" + txID + '\'' +
                '}';
    }
}