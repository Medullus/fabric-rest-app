package com.medullus.fabricrestapi.services;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.dao.purchaseOrder.PurchaseOrderDao;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.mhc.fabric.client.FabricClient;
import org.hyperledger.fabric.sdk.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PurchaseOrderServiceTest {

    @MockBean
    PurchaseOrderDao purchaseOrderDao;

    @MockBean
    FabricClient fabricClient;

    PurchaseOrderService purchaseOrderService;
    PurchaseOrderServicePojo purchaseOrderServicePojo;

    @Before
    public void setup() throws InvalidArgumentException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        purchaseOrderService = new PurchaseOrderService(purchaseOrderDao, fabricClient);
        when(fabricClient.hasMember(any())).thenReturn(true);
        when(purchaseOrderDao.addPurchaseOrder(any())).thenReturn(TestUtil.txId);
        when(purchaseOrderDao.updatePurchaseOrder(any())).thenReturn(TestUtil.txId);


        purchaseOrderServicePojo = new PurchaseOrderServicePojo();
        purchaseOrderServicePojo.setOrg(TestUtil.org);
        purchaseOrderServicePojo.setCaller(TestUtil.caller);
        purchaseOrderServicePojo.setPurchaseOrdersList(Arrays.asList(TestUtil.getPurchaseOrders()));
    }

    @Test
    public void testAddandUpdate(){

        assertNotNull(purchaseOrderService.addPurchaseOrder(purchaseOrderServicePojo));
        assertNotNull(purchaseOrderService.updatePurchaseOrder(purchaseOrderServicePojo));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testAddThrows() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        when(purchaseOrderDao.addPurchaseOrder(any())).thenThrow(ProposalException.class);
        purchaseOrderService.addPurchaseOrder(purchaseOrderServicePojo);
    }

    @Test
    public void testGet() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        when(purchaseOrderDao.getPurchaseOrder(any(), any(), any())).thenReturn(TestUtil.getPurchaseOrders());

        assertEquals(5, purchaseOrderService.getPurchaseOrder("asd", "asd","asd").length);

    }

    @Test(expected = InternalServerErrorException.class)
    public void testgetThrows() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        when(purchaseOrderDao.getPurchaseOrder(any(), any(), any())).thenThrow(ProposalException.class);
        purchaseOrderService.getPurchaseOrder("any", "any", "an ");
    }

}
