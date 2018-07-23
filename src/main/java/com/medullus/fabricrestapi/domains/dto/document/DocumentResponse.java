package com.medullus.fabricrestapi.domains.dto.document;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel
public class DocumentResponse {

    @ApiModelProperty
    private ResponseHeader responseHeader;

    @ApiModelProperty
    private Map<String, Object> document;

    @ApiModelProperty
    private Map<String, String> txResults;

    public Map<String, Object> getDocument() {
        return document;
    }

    public void setDocument(Map<String, Object> document) {
        this.document = document;
    }

    public Map<String, String> getTxResults() {
        return txResults;
    }

    public void setTxResults(Map<String, String> txResults) {
        this.txResults = txResults;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    @Override
    public String toString() {
        return "DocumentResponse{" +
                "responseHeader=" + responseHeader +
                ", document=" + document +
                ", txResults=" + txResults +
                '}';
    }
}
