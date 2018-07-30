package com.medullus.fabricrestapi.dao.purchaseOrder;

import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
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
import static com.medullus.fabricrestapi.utils.Constants.PURCHASEORDER_GET;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;

@Component
public class PurchaseOrderDao {
    private static Logger logger = Logger.getLogger(PurchaseOrderDao.class);

    private FabricClient fabricClient;
    private FabricConfig fabricConfig;

    @Autowired
    public PurchaseOrderDao(FabricClient fabricClient, FabricConfig fabricConfig){
        this.fabricClient = fabricClient;
        this.fabricConfig = fabricConfig;
    }

    public String updatePurchaseOrder(PurchaseOrderServicePojo purchaseOrderServicePojo) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        return invokeHelper(purchaseOrderServicePojo, PURCHASEORDER_UPDATE);
    }

    public String addPurchaseOrder(PurchaseOrderServicePojo purchaseOrderServicePojo) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        return invokeHelper(purchaseOrderServicePojo, PURCHASEORDER_ADD);
    }

    public PurchaseOrder[] getPurchaseOrder(String poNumber, String caller, String org) throws IOException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException {
        String[] args = new String[]{poNumber};
        String payload;
        PurchaseOrder[] purchaseOrders;
        try {
            payload = fabricClient.query(caller, PURCHASEORDER_GET, args, getChaincodeInfo());
            logger.debug("got payload:");
            logger.debug(payload);
            purchaseOrders = PurchaseOrderDaoMapper.mapArgsToPurchaseOrder(payload);
        } catch (IOException|ProposalException|ClassNotFoundException|CryptoException|NetworkConfigurationException|ExecutionException|InvalidArgumentException|IllegalAccessException|TransactionException|InterruptedException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return purchaseOrders;
    }

    protected String invokeHelper(PurchaseOrderServicePojo purchaseOrderServicePojo, String fcn) throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        String[] args = new String[1];
        args[0] = PurchaseOrderDaoMapper.mapPurchaseOrderToArgs(purchaseOrderServicePojo.getPurchaseOrdersList());
        String txId;
        try {
            txId = fabricClient.invoke(purchaseOrderServicePojo.getCaller(), fcn, args, getChaincodeInfo()).get();
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

