package com.medullus.fabricrestapi;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;

import java.util.Arrays;

public class TestUtil {
    public final static String fcn = "fakefcn";
    public final static String caller = "fakeCaller";
    public final static String channel = "foo";
    public final static String ccName = "ccName";
    public final static String ccVer = "ccVer";
    public final static String txId = "faketxID";
    public final static String org = "org1";

    public static RequestHeader getReqHeader() {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setCaller(caller);
        requestHeader.setOrg(org);
        return requestHeader;
    }

    public static ResponseHeader getResponseHeader() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage("blank");
        responseHeader.setTxID(txId);
        return responseHeader;
    }

    /************
     * INVOICES *
     ************/
    public static Invoice[] getInvoices() {
        Invoice[] invoices = new Invoice[5];
        for (int i = 0; i < 5; i++) {
            Invoice invoice = new Invoice();
            invoice.setAmount("100,00");
            invoice.setBuyer("cary");
            invoice.setCurrency("USD");
            invoice.setDate("10032001");
            invoice.setPoNumber("A10000");
            invoice.setQuantity("100");
            invoice.setRefId("X8273" + i);
            invoice.setSeller("seller");
            invoice.setSku("sku" + i);
            invoice.setUnitCost("100");

            invoices[i] = invoice;
        }
        return invoices;
    }

    public static InvoiceServicePojo getInvoiceServicePojo() {
        InvoiceServicePojo invoiceServicePojo = new InvoiceServicePojo();
        invoiceServicePojo.setCaller(caller);
        invoiceServicePojo.setInvoices(Arrays.asList(getInvoices()));
        invoiceServicePojo.setOrg("org1");

        return invoiceServicePojo;
    }

    /*****************
     * ENTITY MASTER *
     *****************/
    public static EntityMaster[] getEntityMasters() {
        EntityMaster[] entityMasters = new EntityMaster[5];
        for (int i = 0; i < 5; i++) {
            EntityMaster entityMaster = new EntityMaster();
            entityMaster.setSubName("Atlas Holding");
            entityMaster.setGlEntityCode("A0");
            entityMaster.setGroup("A1");
            entityMaster.setFnlCurr("USD");
            entityMaster.setCountry("US");
            entityMaster.setPaymasterEligible("Y");
            entityMaster.setPaymaster("A0");
            entityMaster.setBank("BOFAUS3" + i);
            entityMaster.setAccount("65987123" + i);
            entityMaster.setNettingSettRules("No restrict");
            entityMaster.setAdditionalReview("N");
            entityMaster.setWht("N");

            entityMasters[i] = entityMaster;
        }
        return entityMasters;
    }

    /*******************
     * PURCHASE ORDERS *
     *******************/
    public static PurchaseOrder[] getPurchaseOrders() {
        PurchaseOrder[] purchaseOrders = new PurchaseOrder[5];
        for (int i = 0; i < 5; i++) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setBuyer("A5");
            purchaseOrder.setDoc("PO");
            purchaseOrder.setSeller("A3");
            purchaseOrder.setSku("85412");
            purchaseOrder.setQuantity("200");
            purchaseOrder.setCurrency("EUR");
            purchaseOrder.setUnitCost("200");
            purchaseOrder.setAmount("40,000");
            purchaseOrder.setType("STD");

            purchaseOrders[i] = purchaseOrder;
        }
        return purchaseOrders;
    }


    /*******************
     * ERRORTRAN ORDERS *
     *******************/
    public static ErrorTransaction[] getErrorTransactions() {
        ErrorTransaction[] errorTransactions = new ErrorTransaction[5];
        for (int i = 0; i < 5; i++) {
            ErrorTransaction errorTransaction = new ErrorTransaction();
            errorTransaction.setSeller("A6");
            errorTransaction.setBuyer("A2");
            errorTransaction.setPoNum("A9854");
            errorTransaction.setSku("23598");
            errorTransaction.setQuantity("180");
            errorTransaction.setCurrency("USD");
            errorTransaction.setUnit("100");
            errorTransaction.setAmount("18,000");
            errorTransaction.setInvStts("error");
            errorTransaction.setDisputeReason("PO Exceed NTE quantity");
            errorTransaction.setDisputeResDate("25-Jan");
            errorTransaction.setDisputeResSteps("Old PO amended for the quantity");

            errorTransactions[i] = errorTransaction;
        }
        return errorTransactions;
    }



}
