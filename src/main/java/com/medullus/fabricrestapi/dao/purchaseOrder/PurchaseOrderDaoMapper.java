package com.medullus.fabricrestapi.dao.purchaseOrder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.medullus.fabricrestapi.dao.invoice.InvoiceDaoMapper;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class PurchaseOrderDaoMapper {
    private static Logger logger = Logger.getLogger(PurchaseOrderDaoMapper.class);

    public static String mapPurchaseOrderToArgs(List<PurchaseOrder> purchaseOrders){
        Gson gson = new Gson();
        StringJoiner sj = new StringJoiner(",", "[","]");
        for(PurchaseOrder purchaseOrder : purchaseOrders){
            sj.add(gson.toJson(purchaseOrder, PurchaseOrder.class));
        }
        logger.debug("mapPurchaseOrderToArgs");
        logger.debug(sj.toString());

        return sj.toString();
    }

    public static PurchaseOrder[] mapArgsToPurchaseOrder(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PurchaseOrder[] purchaseOrders;
        try {
            purchaseOrders = objectMapper.readValue(payload, PurchaseOrder[].class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }

        return purchaseOrders;
    }
}
