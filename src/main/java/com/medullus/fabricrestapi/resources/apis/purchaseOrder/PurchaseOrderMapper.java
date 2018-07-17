package com.medullus.fabricrestapi.resources.apis.purchaseOrder;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderRequest;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderResponse;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.medullus.fabricrestapi.services.PurchaseOrderService;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PurchaseOrderMapper {
    private PurchaseOrderService purchaseOrderService;
    private Mapper mapper;

    @Autowired
    public PurchaseOrderMapper(PurchaseOrderService purchaseOrderService){
        this.purchaseOrderService = purchaseOrderService;
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }


    public PurchaseOrderServicePojo mapResourceToServicePojo(PurchaseOrderRequest purchaseOrderRequest){
        PurchaseOrderServicePojo validPurchaseOrderStub = mapper.map(purchaseOrderRequest, PurchaseOrderServicePojo.class);

        return validPurchaseOrderStub;
    }

    //TODO
    /**
     * Note this is in direction back to client
     * **/
    public PurchaseOrderResponse mapServiceObjToResponse(PurchaseOrder[] purchaseOrders, String message, String txId){
        PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
        if(purchaseOrders != null){
            purchaseOrderResponse.setPurchaseOrder(Arrays.asList(purchaseOrders));
        }//TODO need to check purchaseOrders not null, not purchaseOrderResponse
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);
        responseHeader.setTxID(txId);
        purchaseOrderResponse.setResponseHeader(responseHeader);

        return purchaseOrderResponse;
    }

    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            final String purchaseOrders = "purchaseOrders";
            @Override
            protected void configure() {
                mapping(PurchaseOrderRequest.class, PurchaseOrderServicePojo.class)
                        .fields("requestHeader.caller", "caller")
                        .fields("requestHeader.org", "org")
                        .fields("purchaseOrderList", "purchaseOrdersList");
            }
        };
    }
}
