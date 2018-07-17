package com.medullus.fabricrestapi.dao.invoice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class InvoiceDaoMapperTest {

    private Invoice[] invoices;
    private String invoiceArgs;

    @Before
    public void setup() throws JsonProcessingException {
        invoices = TestUtil.getInvoices();
        ObjectMapper mapper = new ObjectMapper();
        invoiceArgs = mapper.writeValueAsString(invoices);
    }

    @Test
    public void testNumInvoice() throws IOException {
        Invoice[] invoicesTest = InvoiceDaoMapper.mapArgsToInvoices(invoiceArgs);
        assertEquals(5, invoicesTest.length);
    }

    @Test
    public void testCheckRefId() throws IOException {
        Invoice[] invoicesTest = InvoiceDaoMapper.mapArgsToInvoices(invoiceArgs);
        String refId = "X8273";
        int i = 0;
        for(Invoice invoice : invoicesTest){
            String tempRef = refId+i;
            assertEquals(tempRef, invoice.getRefId());
            i++;
        }
    }

    @Test
    public void testMapInvoiceToArgs(){
        String args;
        args = InvoiceDaoMapper.mapInvoiceToArgs(Arrays.asList(invoices));

        assertEquals(args, invoiceArgs);
    }

    @Test(expected = IOException.class)
    public void testMapArgsToInvoiceError() throws IOException {
        String badString = "badArgs";

        InvoiceDaoMapper.mapArgsToInvoices(badString);
    }
}
