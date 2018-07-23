package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.document.DocumentDao;
import com.medullus.fabricrestapi.domains.dto.document.Document;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServiceBulkPojo;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.domains.dto.document.TXID;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.mhc.fabric.client.FabricClient;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private static final Logger logger = Logger.getLogger(DocumentService.class);
    private DocumentDao documentDao;
    private FabricClient fabricClient;

    @Autowired
    public DocumentService(DocumentDao documentDao, FabricClient fabricClient){
        this.documentDao = documentDao;
        this.fabricClient = fabricClient;
    }

    public Map<String, String> addDocument(DocumentServiceBulkPojo documentServiceBulkPojo, String documentPK, String caller) throws ExecutionException, InterruptedException {
        checkCaller(caller);


        //get a list and run each async
        List<Map<String, Object>> docList = documentServiceBulkPojo.getDocuments();
        List<String> txIds = new ArrayList<>();
        DocumentServicePojo documentServicePojo = new DocumentServicePojo();
        CompletableFuture<TXID> txId;

        Map<String, CompletableFuture<TXID>> futMap = new HashMap<>();

        for(Map<String,Object> doc : docList){
            documentServicePojo.setCaller(documentServiceBulkPojo.getCaller());
            documentServicePojo.setDocument(doc);

            try {
                String pk = doc.get("documentPK").toString();
                txId = documentDao.addDocument(documentServicePojo,pk);

                futMap.put(pk, txId);

            } catch (ProposalException |TransactionException |CryptoException |NetworkConfigurationException |InvalidArgumentException |IllegalAccessException|InterruptedException|ExecutionException |InvocationTargetException |NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
                e.printStackTrace();
                logger.error(e);
                throw new InternalServerErrorException(e.getMessage());
            }
        }

        Map<String, String> results = new HashMap<>();


        while(!futMap.isEmpty()){

            futMap.forEach((k, v) ->{

                if(v.isDone()){
                    try {
                        results.put(k, v.get().getTxId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            futMap.keySet().removeAll(results.keySet());
        }

        logger.debug("Futures complete with tx number : "+results.size());

        return results;

    }

    public String getDocument(String documentPK, String caller, String org){
        checkCaller(caller);
        String documents;

        try {
            documents = documentDao.getDocument(documentPK, caller, org);
        } catch (IOException |ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        return documents;
    }


    @Async
    public void checkCaller(String caller){
        logger.debug("Checking caller:"+caller);
        try {

            fabricClient.hasMember(caller);
        } catch (InvalidArgumentException e) {
            logger.error(e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
