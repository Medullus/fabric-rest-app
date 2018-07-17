package com.medullus.fabricrestapi.domains.dto.errorTransaction;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ErrorTransactionResponse {
    @ApiModelProperty
    private ResponseHeader responseHeader;

    @ApiModelProperty
    private List<ErrorTransaction> errorTransactions;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public List<ErrorTransaction> getErrorTransactions() { return errorTransactions; }

    public void setErrorTransactions(List<ErrorTransaction> errorTransactions) {
        this.errorTransactions = errorTransactions;
    }

    @Override
    public String toString() {
        return "EntityMasterResponse{" +
                "responseHeader=" + responseHeader +
                ", errorTransactions=" + errorTransactions +
                '}';
    }
}
