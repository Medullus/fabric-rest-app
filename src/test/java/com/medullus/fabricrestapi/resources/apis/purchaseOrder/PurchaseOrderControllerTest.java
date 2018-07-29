package com.medullus.fabricrestapi.resources.apis.purchaseOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderRequest;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderResponse;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.services.PurchaseOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseOrderController.class)
public class PurchaseOrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PurchaseOrderService mockPurchaseOrderService;

    @MockBean
    PurchaseOrderMapper mockPurchaseOrderMapper;

    PurchaseOrderRequest purchaseOrderRequest;
    PurchaseOrderResponse purchaseOrderResponse;
    PurchaseOrder[] purchaseOrders;

    final String poUrl = "/purchaseorders";
    String jsonString;
    final String badJson = "badString";
    final String poNumber = "poNumber123";

    @Before
    public void setup() throws JsonProcessingException {


        purchaseOrders = TestUtil.getPurchaseOrders();

        this.purchaseOrderRequest = new PurchaseOrderRequest();
        this.purchaseOrderResponse = new PurchaseOrderResponse();

        purchaseOrderRequest.setRequestHeader(TestUtil.getReqHeader());
        purchaseOrderRequest.setPurchaseOrderList(Arrays.asList(purchaseOrders));

        purchaseOrderResponse.setResponseHeader(TestUtil.getResponseHeader());
        purchaseOrderResponse.setPurchaseOrder(Arrays.asList(purchaseOrders));

        doCallRealMethod().when(mockPurchaseOrderMapper).mapServiceObjToResponse(TestUtil.getPurchaseOrders(), "blank", TestUtil.txId);
        doCallRealMethod().when(mockPurchaseOrderMapper).mapResourceToServicePojo(purchaseOrderRequest);

        doReturn(TestUtil.txId).when(mockPurchaseOrderService).addPurchaseOrder(any(PurchaseOrderServicePojo.class));
        doReturn(TestUtil.txId).when(mockPurchaseOrderService).updatePurchaseOrder(any(PurchaseOrderServicePojo.class));
        doReturn(purchaseOrders).when(mockPurchaseOrderService).getPurchaseOrder(anyString(), anyString(), anyString());

        this.jsonString = new ObjectMapper().writeValueAsString(purchaseOrderRequest);

    }

    /**
     * Test Add Purchase Order
     **/

    @Test
    public void testAddPurchaseOrderOK() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(purchaseOrderRequest);

        this.mockMvc
                .perform(post(poUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testAddPurchaseOrderFail() throws Exception {

        this.mockMvc
                .perform(post(poUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(badJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddPurchaseOrderFail500() throws Exception {

        //THIS IS IMPORTANT!! Syntax! TODO different from when clause in setup
        doThrow(InternalServerErrorException.class).when(mockPurchaseOrderService).addPurchaseOrder(any());

        this.mockMvc
                .perform(post(poUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    /**
     * Test Update Purchase Order
     **/

    @Test
    public void testUpdatePurchaseOrderOk() throws Exception {

        this.mockMvc
                .perform(put(poUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        this.mockMvc
                .perform(put(poUrl).accept(MediaType.APPLICATION_JSON).content(badJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate500Error() throws Exception {
        doThrow(InternalServerErrorException.class).when(mockPurchaseOrderService).updatePurchaseOrder(any());

        this.mockMvc
                .perform(put(poUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    /**
     * Test Retrieve Purchase Order
     **/

    @Test
    public void testRetrievePurchaseOrder() throws Exception {
        this.mockMvc
                .perform(get(poUrl + "/" + poNumber)
                        .header("caller", TestUtil.caller)
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrievePurchaseOrderFailNoCaller() throws Exception {

        this.mockMvc
                .perform(get(poUrl + "/" + poNumber)
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRetrievePurchaseOrder500() throws Exception {

        when(mockPurchaseOrderService.getPurchaseOrder(any(), any(), any())).thenThrow(InternalServerErrorException.class);
        this.mockMvc
                .perform(get(poUrl + "/" + poNumber)
                        .header("caller", "caller")
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }
}
