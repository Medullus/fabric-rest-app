package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.document.DocumentDao;
import com.medullus.fabricrestapi.domains.dto.document.Document;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.mhc.fabric.client.FabricClient;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

@Service
public class DocumentService {
    private static final Logger logger = Logger.getLogger(InvoiceService.class);
    private DocumentDao documentDao;
    private FabricClient fabricClient;

    @Autowired
    public DocumentService(DocumentDao documentDao, FabricClient fabricClient){
        this.documentDao = documentDao;
        this.fabricClient = fabricClient;
    }

    public String addDocument(DocumentServicePojo documentServicePojo, String documentPK, String caller){
        checkCaller(caller);

        try {
            return documentDao.addDocument(documentServicePojo, documentPK);
        } catch (ProposalException |TransactionException |CryptoException |NetworkConfigurationException |InvalidArgumentException |IllegalAccessException|InterruptedException|ExecutionException |InvocationTargetException |NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
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

    private void checkCaller(String caller){
        try {
            fabricClient.hasMember(caller);
        } catch (InvalidArgumentException e) {
            logger.error(e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
