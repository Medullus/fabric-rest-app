package com.medullus.fabricrestapi.dao.purchaseOrder;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrder;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.medullus.fabricrestapi.utils.Constants.PURCHASEORDER_ADD;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PurchaseOrderDaoTest {

    @MockBean
    private FabricClient mockFabricClient;

    @MockBean
    private FabricConfig mockFabricConfig;

    @MockBean
    private NetworkConfig mockNetworkConfig;

    private PurchaseOrderDao purchaseOrderDao;

    private PurchaseOrderServicePojo purchaseOrderServicePojo;

    @Before
    public void setup() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        this.purchaseOrderDao = new PurchaseOrderDao(mockFabricClient, mockFabricConfig);

        when(mockFabricClient.getNetworkConfig()).thenReturn(mockNetworkConfig);
        Set<String> mockSetString = mock(Set.class);
        Iterator mockIterator = mock(Iterator.class);
        when(mockNetworkConfig.getChannelNames()).thenReturn(mockSetString);
        when(mockSetString.iterator()).thenReturn(mockIterator);
        when(mockIterator.next()).thenReturn(TestUtil.channel);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCNAME)).thenReturn(TestUtil.ccName);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCVER)).thenReturn(TestUtil.ccVer);
        when(mockFabricClient.invoke(any(),any(), any(), any())).thenReturn(TestUtil.getFut());

        this.purchaseOrderServicePojo = new PurchaseOrderServicePojo();
        purchaseOrderServicePojo.setCaller(TestUtil.caller);
        purchaseOrderServicePojo.setOrg(TestUtil.org);
        PurchaseOrder[] pos = TestUtil.getPurchaseOrders();
        List<PurchaseOrder> listPO = new ArrayList(Arrays.asList(pos));
        purchaseOrderServicePojo.setPurchaseOrdersList(listPO);

    }

    @Test
    public void testInvokeHelper() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, TransactionException, ProposalException {
        assertNotNull(purchaseOrderDao.invokeHelper(this.purchaseOrderServicePojo, "fcn"));
    }

    @Test(expected = ProposalException.class)
    public void testInvokeHelperThrow() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        when(mockFabricClient.invoke(any(), any(), any(), any())).thenThrow(ProposalException.class);

        purchaseOrderDao.invokeHelper(this.purchaseOrderServicePojo, "fcn");
    }

    @Test
    public void testGetPO() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        String pos = "[{\n" +
                "      \"amount\": 40000,\n" +
                "      \"buyer\": \"A5\",\n" +
                "      \"currency\": \"EUR\",\n" +
                "      \"doc\": \"PO\",\n" +
                "      \"quantity\": 200,\n" +
                "      \"refId\": \"A1235\",\n" +
                "      \"seller\": \"A3\",\n" +
                "      \"sku\": 85412,\n" +
                "      \"type\": \"STD\",\n" +
                "      \"unitCost\": 200\n" +
                "    }]";
        when(mockFabricClient.query(any(), any(), any(), any())).thenReturn(pos);

        PurchaseOrder[] purchaseOrders = purchaseOrderDao.getPurchaseOrder("any", "any", "any");

        assertEquals(1, purchaseOrders.length);
        assertEquals("EUR", purchaseOrders[0].getCurrency());
    }

    @Test(expected = ProposalException.class)
    public void testGetPOException() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        when(mockFabricClient.query(any(), any(), any(), any())).thenThrow(ProposalException.class);

        purchaseOrderDao.getPurchaseOrder("any", "any", "any");
    }

    @Test
    public void testAddAndUpdate() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        purchaseOrderDao.addPurchaseOrder(this.purchaseOrderServicePojo);
        purchaseOrderDao.updatePurchaseOrder(this.purchaseOrderServicePojo);
    };



}
