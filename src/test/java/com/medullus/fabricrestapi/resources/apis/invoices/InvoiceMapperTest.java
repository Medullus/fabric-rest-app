package com.medullus.fabricrestapi.resources.apis.invoices;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceResponse;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.services.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class InvoiceMapperTest {

    @MockBean
    InvoiceService mockInvoiceService;

    InvoiceMapper invoiceMapper;
    InvoiceRequest invoiceRequest;

    @Before
    public void setup(){
        invoiceMapper = new InvoiceMapper(mockInvoiceService);
        Invoice[] invoices = TestUtil.getInvoices();
        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoices(Arrays.asList(invoices));
        invoiceRequest.setRequestHeader(TestUtil.getReqHeader());
    }

    @Test
    public void testMapResourceToServicePojo(){
        InvoiceServicePojo invoiceServicePojo = invoiceMapper.mapResourceToServicePojo(invoiceRequest);

        assertEquals(TestUtil.caller, invoiceServicePojo.getCaller());
        assertEquals(5, invoiceServicePojo.getInvoices().size());

        for(Invoice invoice : invoiceServicePojo.getInvoices()){
            assertEquals("100,00", invoice.getAmount());
        }

    }

    @Test
    public void testMapServiceObjToResponse(){
        InvoiceResponse invoiceResponse = invoiceMapper.mapServiceObjToResponse(TestUtil.getInvoices(), "blank", TestUtil.txId);

        assertEquals("blank", invoiceResponse.getResponseHeader().getMessage());
        assertEquals(TestUtil.txId, invoiceResponse.getResponseHeader().getTxID());
        assertEquals(5, invoiceResponse.getInvoices().size());
    }
}
