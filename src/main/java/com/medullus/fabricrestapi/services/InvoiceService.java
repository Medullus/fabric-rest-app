package com.medullus.fabricrestapi.services;


import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
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
public class InvoiceService {

    private static final Logger logger = Logger.getLogger(InvoiceService.class);

    private InvoiceDao invoiceDao;
    private FabricClient fabricClient;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao, FabricClient fabricClient){
        this.invoiceDao = invoiceDao;
        this.fabricClient = fabricClient;
    }

    public String updateInvoice(InvoiceServicePojo invoiceServicePojo){
        checkCaller(invoiceServicePojo.getCaller());

        try {
            return invoiceDao.updateInvoice(invoiceServicePojo);
        } catch (ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * @return txid
     * **/
    public String addInvoice(InvoiceServicePojo invoiceServicePojo){
        checkCaller(invoiceServicePojo.getCaller());

        try {
            return invoiceDao.addInvoices(invoiceServicePojo);
        } catch (ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public Invoice[] getInvoice(String refId, String poNumber, String caller, String org){
        checkCaller(caller);
        Invoice[] invoices;

        try {
            invoices = invoiceDao.getInvoice(refId, poNumber, caller, org );
        } catch (IOException|ProposalException|TransactionException|CryptoException|NetworkConfigurationException|InvalidArgumentException|IllegalAccessException|InterruptedException|ExecutionException|InvocationTargetException|NoSuchMethodException|ClassNotFoundException|InstantiationException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
        return invoices;
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
