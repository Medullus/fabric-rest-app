package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.entityMaster.EntityMasterDao;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
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
public class EntityMasterService {
    private static final Logger logger = Logger.getLogger(EntityMasterService.class);

    private EntityMasterDao entityMasterDao;
    private FabricClient fabricClient;

    @Autowired
    public EntityMasterService(EntityMasterDao entityMasterDao, FabricClient fabricClient){
        this.entityMasterDao = entityMasterDao;
        this.fabricClient = fabricClient;
    }

    public String updateEntityMaster(EntityMasterServicePojo entityMasterServicePojo){
        checkCaller(entityMasterServicePojo.getCaller());

        try {
            return entityMasterDao.updateEntityMaster(entityMasterServicePojo);
        } catch (ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * @return txid
     * **/
    public String addEntityMaster(EntityMasterServicePojo entityMasterServicePojo){
        checkCaller(entityMasterServicePojo.getCaller());

        try {
            return entityMasterDao.addEntityMaster(entityMasterServicePojo);
        } catch (ProposalException |TransactionException |CryptoException |NetworkConfigurationException |InvalidArgumentException |IllegalAccessException|InterruptedException|ExecutionException |InvocationTargetException |NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public EntityMaster[] getEntityMaster(String entityKey, String caller, String org){
        checkCaller(caller);
        EntityMaster[] entityMasters;

        try {
            entityMasters = entityMasterDao.getEntityMaster(entityKey, caller, org);
        } catch (IOException |ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        return entityMasters;
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
