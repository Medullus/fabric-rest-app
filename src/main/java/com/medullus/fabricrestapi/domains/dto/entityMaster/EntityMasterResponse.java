package com.medullus.fabricrestapi.domains.dto.entityMaster;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class EntityMasterResponse {

    @ApiModelProperty
    private ResponseHeader responseHeader;

    @ApiModelProperty
    private List<EntityMaster> entityMasters;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public List<EntityMaster> getEntityMasters() { return entityMasters; }

    public void setEntityMasters(List<EntityMaster> entityMasters) {
        this.entityMasters = entityMasters;
    }

    @Override
    public String toString() {
        return "EntityMasterResponse{" +
                "responseHeader=" + responseHeader +
                ", entityMasters=" + entityMasters +
                '}';
    }
}
