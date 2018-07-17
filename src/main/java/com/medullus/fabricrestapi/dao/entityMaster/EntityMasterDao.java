package com.medullus.fabricrestapi.dao.entityMaster;

import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import com.mhc.fabric.client.models.ChaincodeInfo;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static com.medullus.fabricrestapi.utils.Constants.*;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;

@Component
public class EntityMasterDao {
    private static Logger logger = Logger.getLogger(EntityMasterDao.class);

    //FabricClient instance goes here for invoke/query calls
    private FabricClient fabricClient;
    private FabricConfig fabricConfig;

    @Autowired
    public EntityMasterDao(FabricClient fabricClient, FabricConfig fabricConfig){
        this.fabricClient = fabricClient;
        this.fabricConfig = fabricConfig;
    }

    //returns txid
    public String updateEntityMaster(EntityMasterServicePojo entityMasterServicePojo) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        return invokeHelper(entityMasterServicePojo, ENTITYMASTER_UPDATE);
    }

    //return txid
    public String addEntityMaster(EntityMasterServicePojo entityMasterServicePojo) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        return invokeHelper(entityMasterServicePojo, ENTITYMASTER_ADD);
    }

    private String invokeHelper(EntityMasterServicePojo entitymasterServicePojo, String fcn) throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        String[] args = new String[1];
        args[0] = EntityMasterDaoMapper.mapEntityMasterToArgs(entitymasterServicePojo.getEntityMasters());
        String txId;
        try {
            txId = fabricClient.invoke(entitymasterServicePojo.getCaller(), fcn, args, getChaincodeInfo());
        } catch (ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return txId;
    }

    public EntityMaster[] getEntityMaster(String entityKey, String caller, String org) throws IOException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException {
        String[] args = new String[]{entityKey};
        String payload;
        EntityMaster[] entityMasters;
        try {
            payload = fabricClient.query(caller, ENTITYMASTER_GET, args, getChaincodeInfo());
            logger.debug("got payload:");
            logger.debug(payload);
            entityMasters = EntityMasterDaoMapper.mapArgsToEntityMaster(payload);
        } catch (IOException|ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return entityMasters;
    }

    private ChaincodeInfo getChaincodeInfo(){
        NetworkConfig networkConfig = fabricClient.getNetworkConfig();
        String channel = networkConfig.getChannelNames().iterator().next();
        ChaincodeInfo chaincodeInfo = new ChaincodeInfo(fabricConfig.getProperty(MHC_FABRIC_CCNAME),fabricConfig.getProperty(MHC_FABRIC_CCVER),channel );
        return chaincodeInfo;
    }
}
