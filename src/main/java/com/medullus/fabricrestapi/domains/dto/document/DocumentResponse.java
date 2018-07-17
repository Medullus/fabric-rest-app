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
    private Map<String, Object> documents;


    public Map<String, Object> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, Object> documents) {
        this.documents = documents;
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
                ", documents=" + documents +
                '}';
    }
}
