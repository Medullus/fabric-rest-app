package com.medullus.fabricrestapi.resources.apis.errorTransaction;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionRequest;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionResponse;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionServicePojo;
import com.medullus.fabricrestapi.services.ErrorTransactionService;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ErrorTransactionMapper {

    private ErrorTransactionService errorTransactionService;
    private Mapper mapper;

    @Autowired
    public ErrorTransactionMapper(ErrorTransactionService errorTransactionService){
        this.errorTransactionService = errorTransactionService;
        this.mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }

    public ErrorTransactionResponse mapServiceObjToResponse(ErrorTransaction[] errorTransactions, String message, String txId){
        ErrorTransactionResponse errorTransactionResponse = new ErrorTransactionResponse();
        if(errorTransactionResponse != null){
            errorTransactionResponse.setErrorTransactions(Arrays.asList(errorTransactions));
        }
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);
        responseHeader.setTxID(txId);
        errorTransactionResponse.setResponseHeader(responseHeader);

        return errorTransactionResponse;
    }

    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            final String errorTransactions = "errorTransactions";
            @Override
            protected void configure() {
                mapping(ErrorTransactionRequest.class, ErrorTransactionServicePojo.class)
                        .fields("requestHeader.caller", "caller")
                        .fields("requestHeader.org", "org")
                        .fields(errorTransactions, errorTransactions);
            }
        };
    }
}
