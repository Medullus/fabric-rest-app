package com.medullus.fabricrestapi.dao.document;

import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.document.Document;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.domains.dto.document.TXID;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import com.mhc.fabric.client.models.ChaincodeInfo;
import io.netty.util.concurrent.CompleteFuture;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.medullus.fabricrestapi.utils.Constants.*;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;

@Component
public class DocumentDao {
    private static Logger logger = Logger.getLogger(InvoiceDao.class);

    private FabricClient fabricClient;
    private FabricConfig fabricConfig;

    @Autowired
    public DocumentDao(FabricClient fabricClient, FabricConfig fabricConfig){
        this.fabricClient = fabricClient;
        this.fabricConfig = fabricConfig;
    }

    @Async
    public CompletableFuture<TXID> addDocument(DocumentServicePojo documentServicePojo, String documentPK) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        String[] args = new String[]{DocumentDaoMapper.mapDocumentToArgs(documentServicePojo.getDocument()),documentPK};

        return CompletableFuture.completedFuture(new TXID(invokeHelper(args, DOCUMENT_ADD, documentServicePojo.getCaller())));
    }

    public String getDocument(String documentPK, String caller, String org) throws IOException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException {
        String[] args = new String[]{documentPK};
        String payload;
        try {
            payload = fabricClient.query(caller, DOCUMENT_GET, args, getChaincodeInfo());
            logger.debug("got payload:");
            logger.debug(payload);
        } catch (ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return payload;
    }


    private CompletableFuture<String> invokeHelper(String[] args, String fcn, String caller) throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {

        CompletableFuture<String> txId;
        try {
            txId = fabricClient.invoke(caller, fcn, args, getChaincodeInfo());
        } catch (ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return txId;
    }

    private ChaincodeInfo getChaincodeInfo(){
        NetworkConfig networkConfig = fabricClient.getNetworkConfig();
        String channel = networkConfig.getChannelNames().iterator().next();
        ChaincodeInfo chaincodeInfo = new ChaincodeInfo(fabricConfig.getProperty(MHC_FABRIC_CCNAME),fabricConfig.getProperty(MHC_FABRIC_CCVER),channel );
        return chaincodeInfo;
    }
}
