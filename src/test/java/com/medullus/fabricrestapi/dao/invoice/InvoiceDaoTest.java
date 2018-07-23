package com.medullus.fabricrestapi.dao.invoice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import com.mhc.fabric.client.models.ChaincodeInfo;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class InvoiceDaoTest {

    private Invoice[] invoices;
    private InvoiceServicePojo invoiceServicePojo;
    private String argsInvoices;

    @MockBean
    FabricClient mockFabricClient;

    @MockBean
    FabricConfig mockFabricConfig;

    @MockBean
    NetworkConfig mockNetworkConfig;


    InvoiceDao invoiceDao;
    String txId = TestUtil.txId;


    @Before
    public void setup() throws JsonProcessingException {
        invoices = TestUtil.getInvoices();

        ObjectMapper mapper = new ObjectMapper();
        argsInvoices = mapper.writeValueAsString(invoices);

        invoiceDao = new InvoiceDao(mockFabricClient, mockFabricConfig);
        invoiceServicePojo = TestUtil.getInvoiceServicePojo();

        /**
         * Handles mock for private method getChaincodeInfo
         * NetworkConfig networkConfig = fabricClient.getNetworkConfig();
         * String channel = networkConfig.getChannelNames().iterator().next();
         * **/
        doReturn(mockNetworkConfig).when(mockFabricClient).getNetworkConfig();
        Set<String> mockSetString = mock(Set.class);
        Iterator mockIterator = mock(Iterator.class);
        doReturn(mockSetString).when(mockNetworkConfig).getChannelNames();
        doReturn(mockIterator).when(mockSetString).iterator();
        doReturn(TestUtil.channel).when(mockIterator).next();
//        ChaincodeInfo chaincodeInfo = new ChaincodeInfo(fabricConfig.getProperty(MHC_FABRIC_CCNAME),fabricConfig.getProperty(MHC_FABRIC_CCVER),channel );
        doReturn(TestUtil.ccName).when(mockFabricConfig).getProperty(MHC_FABRIC_CCNAME);
        doReturn(TestUtil.ccVer).when(mockFabricConfig).getProperty(MHC_FABRIC_CCVER);

    }

    @Test
    public void testAdd() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {

        doReturn(txId).when(mockFabricClient).invoke(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));

        String returnedTxId = invoiceDao.addInvoices(invoiceServicePojo);
        assertEquals(txId, returnedTxId);
    }

    @Test(expected = ProposalException.class)
    public void testAddThrowException() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {

        doThrow(new ProposalException("bad")).when(mockFabricClient).invoke(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));

        invoiceDao.addInvoices(invoiceServicePojo);
    }

    @Test
    public void testUpdate() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        doReturn(txId).when(mockFabricClient).invoke(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));
        assertEquals(txId, invoiceDao.updateInvoice(invoiceServicePojo));
    }

    @Test(expected = InstantiationException.class)
    public void testUpdateError() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        doThrow(new InstantiationException("bad")).when(mockFabricClient).invoke(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));
        invoiceDao.updateInvoice(invoiceServicePojo);
    }

    @Test
    public void testGetInvoice() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {

        doReturn(argsInvoices).when(mockFabricClient).query(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));

        Invoice[] invoice1 = invoiceDao.getInvoice("dontmatter", "any", "becauseOf", "any()" );

        assertEquals(5, invoice1.length);
        assertEquals("100,00", invoice1[0].getAmount());

    }

    @Test(expected = NetworkConfigurationException.class)
    public void testGetInvoiceError() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException, IOException {
        doThrow(new NetworkConfigurationException("bad")).when(mockFabricClient).query(anyString(), anyString(), any(String[].class), any(ChaincodeInfo.class));

        invoiceDao.getInvoice("dont","any" , "matter","what" );
    }
}
