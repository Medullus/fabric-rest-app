package com.medullus.fabricrestapi.services;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.dao.invoice.InvoiceDao;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
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
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
public class InvoiceServiceTest {

    @MockBean
    InvoiceDao mockInvoiceDao;

    @MockBean
    FabricClient mockFabricClient;

    InvoiceService invoiceService;
    InvoiceServicePojo invoiceServicePojo;
    final String txID = TestUtil.txId;

    @Before
    public void setup() throws InvalidArgumentException, ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        invoiceService = new InvoiceService(mockInvoiceDao, mockFabricClient);
        invoiceServicePojo = TestUtil.getInvoiceServicePojo();

        doReturn(true).when(mockFabricClient).hasMember(anyString());
        doReturn(txID).when(mockInvoiceDao).updateInvoice(invoiceServicePojo);
        doReturn(txID).when(mockInvoiceDao).addInvoices(invoiceServicePojo);
    }

    @Test
    public void testUpdateInvoice(){

        assertEquals(txID, invoiceService.updateInvoice(invoiceServicePojo));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testUpdateInvoiceFail() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        doThrow(new ProposalException("bad")).when(mockInvoiceDao).updateInvoice(invoiceServicePojo);

        invoiceService.updateInvoice(invoiceServicePojo);
    }

    @Test
    public void testAdd(){
        assertEquals(txID, invoiceService.addInvoice(invoiceServicePojo));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testAddFail() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        doThrow(new ProposalException("bad")).when(mockInvoiceDao).addInvoices(invoiceServicePojo);
        invoiceService.addInvoice(invoiceServicePojo);
    }

    @Test
    public void testGetInvoice() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        doReturn(TestUtil.getInvoices()).when(mockInvoiceDao).getInvoice(anyString(), anyString(), anyString(), anyString() );

        Invoice[] invoices = invoiceService.getInvoice("dont", "shit", "matter", "what" );
        assertEquals(5, invoices.length);
        assertEquals("100", invoices[0].getAmount());

    }

    @Test(expected = InternalServerErrorException.class)
    public void testGetInvoiceFail() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        doThrow(new TransactionException("bad")).when(mockInvoiceDao).getInvoice(anyString(),anyString() , anyString(),anyString() );

        invoiceService.getInvoice("any","any" , "string", "here" );

    }

    @Test(expected = BadRequestException.class)
    public void testBadCaller() throws InvalidArgumentException {
        doThrow(new InvalidArgumentException("bad")).when(mockFabricClient).hasMember(anyString());

        invoiceService.addInvoice(invoiceServicePojo);
    }

}
