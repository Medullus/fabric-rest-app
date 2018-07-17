package com.medullus.fabricrestapi.dao.errorTransaction;

import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
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

import static com.medullus.fabricrestapi.utils.Constants.MISMATCHES_GET;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;

@Component
public class ErrorTransactionDao {
    private static Logger logger = Logger.getLogger(ErrorTransactionDao.class);

    private FabricClient fabricClient;
    private FabricConfig fabricConfig;

    @Autowired
    public ErrorTransactionDao(FabricClient fabricClient, FabricConfig fabricConfig){
        this.fabricClient = fabricClient;
        this.fabricConfig = fabricConfig;
    }

    public ErrorTransaction[] getErrorTransaction(String caller, String org) throws IOException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException {
        String payload;
        ErrorTransaction[] errorTransactions;
        try {
            payload = fabricClient.query(caller, MISMATCHES_GET, null, getChaincodeInfo());
            logger.debug("got payload:");
            logger.debug(payload);
            errorTransactions = ErrorTransactionDaoMapper.mapArgsToErrorTransaction(payload);
        } catch (IOException|ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return errorTransactions;
    }

    private ChaincodeInfo getChaincodeInfo(){
        NetworkConfig networkConfig = fabricClient.getNetworkConfig();
        String channel = networkConfig.getChannelNames().iterator().next();
        ChaincodeInfo chaincodeInfo = new ChaincodeInfo(fabricConfig.getProperty(MHC_FABRIC_CCNAME),fabricConfig.getProperty(MHC_FABRIC_CCVER),channel );
        return chaincodeInfo;
    }
}
