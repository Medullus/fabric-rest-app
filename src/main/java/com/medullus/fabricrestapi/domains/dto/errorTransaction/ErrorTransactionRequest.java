package com.medullus.fabricrestapi.domains.dto.errorTransaction;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Generic Header")
public class ErrorTransactionRequest {
    @ApiModelProperty
    private RequestHeader requestHeader;

    @ApiModelProperty
    private List<ErrorTransaction> errorTransactions;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public List<ErrorTransaction> getErrorTransactions() {
        return errorTransactions;
    }

    public void setErrorTransactions(List<ErrorTransaction> errorTransactions) {
        this.errorTransactions = errorTransactions;
    }

    @Override
    public String toString() {
        return "ErrorTransactionRequest{" +
                "requestHeader=" + requestHeader +
                ", errorTransactions=" + errorTransactions +
                '}';
    }
}
