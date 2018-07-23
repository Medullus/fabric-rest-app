package com.medullus.fabricrestapi.domains.dto.document;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel
public class DocumentRequest {

    @ApiModelProperty
    private RequestHeader requestHeader;

    public List<Map<String, Object>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Map<String, Object>> documents) {
        this.documents = documents;
    }

    @ApiModelProperty(example = "[{\"documentPK\":\"required\",\"anyKey1\":\"value1\"},{\"documentPK\":\"required\",\"anyKey1\":\"value1\",\"anyKey2\":\"value2\"}]")
    private List<Map<String, Object>> documents;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }


    @Override
    public String toString() {
        return "DocumentsRequest{" +
                "requestHeader=" + requestHeader +
                ", documents=" + documents +
                '}';
    }
}
