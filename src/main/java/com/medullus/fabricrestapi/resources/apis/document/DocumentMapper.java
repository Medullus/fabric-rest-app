package com.medullus.fabricrestapi.resources.apis.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.document.*;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.services.DocumentService;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class DocumentMapper {

    private DocumentService documentService;
    private Mapper mapper;

    @Autowired
    public DocumentMapper(DocumentService documentService){
        this.documentService = documentService;
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }

    public DocumentServiceBulkPojo mapResourceToServicePojo(DocumentRequest documentRequest){
        DocumentServiceBulkPojo validEntityMasterStub = mapper.map(documentRequest, DocumentServiceBulkPojo.class);
        return validEntityMasterStub;
    }

    public DocumentResponse mapServiceObjToResponse(String documents,String message, Map<String, String> txIdList){
        DocumentResponse documentResponse = new DocumentResponse();

        if(documents != null){
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> docs;
            try {
                docs = om.readValue(documents, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InternalServerErrorException(e.getMessage());
            }
            documentResponse.setDocument(docs);
        }
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);

        documentResponse.setResponseHeader(responseHeader);
        documentResponse.setTxResults(txIdList);

        return documentResponse;
    }

    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            final String documents = "documents";
            @Override
            protected void configure() {
                mapping(DocumentRequest.class, DocumentServiceBulkPojo.class)
                        .fields("requestHeader.caller", "caller")
                        .fields("requestHeader.org", "org")
                        .fields(documents, documents);
            }
        };
    }
}
