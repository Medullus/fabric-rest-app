package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.purchaseOrder.PurchaseOrderDao;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
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
public class PurchaseOrderService {

    private static final Logger logger = Logger.getLogger(InvoiceService.class);

    private PurchaseOrderDao purchaseOrderDao;
    private FabricClient fabricClient;

    @Autowired
    public PurchaseOrderService(PurchaseOrderDao purchaseOrderDao, FabricClient fabricClient){
        this.purchaseOrderDao = purchaseOrderDao;
        this.fabricClient = fabricClient;
    }

    public String updatePurchaseOrder(PurchaseOrderServicePojo purchaseOrderServicePojo){
        checkCaller(purchaseOrderServicePojo.getCaller());

        try {
            return purchaseOrderDao.updatePurchaseOrder(purchaseOrderServicePojo);
        } catch (ProposalException |TransactionException |CryptoException |NetworkConfigurationException |InvalidArgumentException |IllegalAccessException|InterruptedException|ExecutionException |InvocationTargetException |NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * @return txid
     * **/
    public String addPurchaseOrder(PurchaseOrderServicePojo purchaseOrderServicePojo){
        checkCaller(purchaseOrderServicePojo.getCaller());

        try {
            return purchaseOrderDao.addPurchaseOrder(purchaseOrderServicePojo);
        } catch (ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public PurchaseOrder[] getPurchaseOrder(String poNumber, String caller, String org){
        checkCaller(caller);
        PurchaseOrder[] purchaseOrders;

        try {
            purchaseOrders = purchaseOrderDao.getPurchaseOrder(poNumber, caller, org);
        } catch (IOException |ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        return purchaseOrders;
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
