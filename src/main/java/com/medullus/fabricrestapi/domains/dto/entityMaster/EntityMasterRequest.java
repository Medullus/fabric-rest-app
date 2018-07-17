package com.medullus.fabricrestapi.domains.dto.entityMaster;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class EntityMasterRequest {


    @ApiModelProperty
    private RequestHeader requestHeader;

    @ApiModelProperty
    private List<EntityMaster> entityMasters;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public List<EntityMaster> getEntityMasters() {
        return entityMasters;
    }

    public void setEntityMasters(List<EntityMaster> entityMasters) {
        this.entityMasters = entityMasters;
    }

    @Override
    public String toString() {
        return "EntityMasterRequest{" +
                "requestHeader=" + requestHeader +
                ", entityMasters=" + entityMasters +
                '}';
    }
}
