package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.errorTransaction.ErrorTransactionDao;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
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
public class ErrorTransactionService {

    private static final Logger logger = Logger.getLogger(ErrorTransactionService.class);

    private ErrorTransactionDao errorTransactionDao;
    private FabricClient fabricClient;

    @Autowired
    public ErrorTransactionService(FabricClient fabricClient, ErrorTransactionDao errorTransactionDao){
        this.errorTransactionDao = errorTransactionDao;
        this.fabricClient = fabricClient;
    }

    public ErrorTransaction[] getErrorTransaction(String caller, String org){
        checkCaller(caller);
        ErrorTransaction[] errorTransactions;

        try {
            errorTransactions = errorTransactionDao.getErrorTransaction(caller, org);
        } catch (IOException |ProposalException |TransactionException |CryptoException |NetworkConfigurationException |InvalidArgumentException |IllegalAccessException|InterruptedException|ExecutionException |InvocationTargetException |NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        return errorTransactions;
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
