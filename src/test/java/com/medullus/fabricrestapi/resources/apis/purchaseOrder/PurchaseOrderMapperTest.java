package com.medullus.fabricrestapi.resources.apis.purchaseOrder;

import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderRequest;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderResponse;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.medullus.fabricrestapi.services.PurchaseOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PurchaseOrderMapperTest {
    @MockBean
    PurchaseOrderService mockPurchaseOrderService;

    PurchaseOrderMapper purchaseOrderMapper;
    PurchaseOrderRequest purchaseOrderRequest;

    @Before
    public void setup(){
        purchaseOrderMapper = new PurchaseOrderMapper(mockPurchaseOrderService);
        PurchaseOrder[] purchaseOrders = TestUtil.getPurchaseOrders();
        purchaseOrderRequest = new PurchaseOrderRequest();
        purchaseOrderRequest.setPurchaseOrderList(Arrays.asList(purchaseOrders));
        purchaseOrderRequest.setRequestHeader(TestUtil.getReqHeader());
    }

    @Test
    public void testMapResourceToServicePojo(){
        PurchaseOrderServicePojo purchaseOrderServicePojo = purchaseOrderMapper.mapResourceToServicePojo(purchaseOrderRequest);

        assertEquals(TestUtil.caller, purchaseOrderServicePojo.getCaller());
        assertEquals(5, purchaseOrderServicePojo.getPurchaseOrdersList().size());

        for(PurchaseOrder purchaseOrder : purchaseOrderServicePojo.getPurchaseOrdersList()){
            assertEquals("A5", purchaseOrder.getBuyer());
        }

    }

    @Test
    public void testMapServiceObjToResponse(){
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderMapper.mapServiceObjToResponse(TestUtil.getPurchaseOrders(), "blank", TestUtil.txId);

        assertEquals("blank", purchaseOrderResponse.getResponseHeader().getMessage());
        assertEquals(TestUtil.txId, purchaseOrderResponse.getResponseHeader().getTxID());
        assertEquals(5, purchaseOrderResponse.getPurchaseOrders().size());
    }
}
