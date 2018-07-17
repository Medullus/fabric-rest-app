package com.medullus.fabricrestapi.resources.apis.users;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.domains.dto.users.UserRegister;
import com.medullus.fabricrestapi.domains.dto.users.UserRegisterRequest;
import com.medullus.fabricrestapi.domains.dto.users.UserRegisterResponse;
import com.medullus.fabricrestapi.domains.dto.users.UserServicePojo;
import com.medullus.fabricrestapi.services.InvoiceService;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    private final Logger logger = Logger.getLogger(UsersMapper.class);

    private Mapper mapper;//TODO we can autowire DozerBeanMapper instead

    public UsersMapper(){
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }

    public UserServicePojo mapReqToService(UserRegisterRequest userRegisterRequest){
        logger.debug("mapping");
        logger.debug(userRegisterRequest);
        return mapper.map(userRegisterRequest, UserServicePojo.class);
    }

    public UserRegisterResponse mapServiceToResponse(UserRegister userRegister, String message){
        logger.debug("Mapping Service to Response");
        logger.debug(userRegister);
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUserRegister(userRegister);
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);
        userRegisterResponse.setResponseHeader(responseHeader);
        logger.debug(userRegisterResponse);
        return userRegisterResponse;
    }

    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(UserRegisterRequest.class, UserServicePojo.class);
            }
        };
    }
}
