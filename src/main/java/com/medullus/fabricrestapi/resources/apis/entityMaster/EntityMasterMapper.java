package com.medullus.fabricrestapi.resources.apis.entityMaster;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterRequest;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterResponse;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
import com.medullus.fabricrestapi.services.EntityMasterService;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EntityMasterMapper {

    private EntityMasterService entityMasterService;
    private Mapper mapper;

    @Autowired
    public EntityMasterMapper(EntityMasterService entityMasterService){
        this.entityMasterService = entityMasterService;
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }

    public EntityMasterServicePojo mapResourceToServicePojo(EntityMasterRequest entityMasterRequest){
        EntityMasterServicePojo validEntityMasterStub = mapper.map(entityMasterRequest, EntityMasterServicePojo.class);
        return validEntityMasterStub;
    }

    public EntityMasterResponse mapServiceObjToResponse(EntityMaster[] entityMasters, String message, String txId){
        EntityMasterResponse entityMasterResponse = new EntityMasterResponse();
        if(entityMasters != null){
            entityMasterResponse.setEntityMasters(Arrays.asList(entityMasters));
        }
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);
        responseHeader.setTxID(txId);
        entityMasterResponse.setResponseHeader(responseHeader);

        return entityMasterResponse;
    }

    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            final String entityMasters = "entityMasters";
            @Override
            protected void configure() {
                mapping(EntityMasterRequest.class, EntityMasterServicePojo.class)
                        .fields("requestHeader.caller", "caller")
                        .fields("requestHeader.org", "org")
                        .fields(entityMasters, entityMasters);
            }
        };
    }
}
