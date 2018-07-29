package com.medullus.fabricrestapi.resources.apis.invoices;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceResponse;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.services.InvoiceService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService mockInvoiceService;

    @MockBean
    InvoiceMapper mockInvoiceMapper;

    InvoiceRequest invoiceRequest;
    InvoiceResponse invoiceResponse;
    Invoice[] invoices;

    final String invoiceUrl = "/invoices";
    String jsonString;
    final String badJson = "badString";
    final String invoicePK = "invoicePK123";

    @Before
    public void setup() throws JsonProcessingException {

//        mockInvoiceMapper = new InvoiceMapper(mockInvoiceService);

        invoices = TestUtil.getInvoices();

        this.invoiceRequest = new InvoiceRequest();
        this.invoiceResponse = new InvoiceResponse();

        invoiceRequest.setRequestHeader(TestUtil.getReqHeader());
        invoiceRequest.setInvoices(Arrays.asList(invoices));

        invoiceResponse.setResponseHeader(TestUtil.getResponseHeader());
        invoiceResponse.setInvoices(Arrays.asList(invoices));

        when(mockInvoiceService.addInvoice(any())).thenReturn(TestUtil.txId);
        when(mockInvoiceService.updateInvoice(any())).thenReturn(TestUtil.txId);
        when(mockInvoiceService.getInvoice(any(),any(), any(),any())).thenReturn(invoices);

        ObjectMapper objectMapper = new ObjectMapper();
        this.jsonString = objectMapper.writeValueAsString(invoiceRequest);

    }

    /**
     *      Test AddInvoices
     * **/

    @Test
    public void testAddInvoiceOK() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(invoiceRequest);

        this.mockMvc
                .perform(post(invoiceUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void testAddInvoiceFail500() throws Exception {

        //THIS IS IMPORTANT!! Syntax! TODO different from when clause in setup
        doThrow(InternalServerErrorException.class).when(mockInvoiceService).addInvoice(any());

        this.mockMvc
                .perform(post(invoiceUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    /**
     * TestUpdate Invoices
     * **/

    @Test
    public void testUpdateInvoiceOk() throws Exception {

        this.mockMvc
                .perform(put(invoiceUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        this.mockMvc
                .perform(put(invoiceUrl).accept(MediaType.APPLICATION_JSON).content(badJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate500Error() throws Exception {
        when(mockInvoiceService.updateInvoice(any())).thenThrow(InternalServerErrorException.class);

        this.mockMvc
                .perform(put(invoiceUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    /**
     *  TestRetrieveInvoice
     * **/

    @Test
    public void testRetrieveInvoice() throws Exception {
        this.mockMvc
                .perform(get(invoiceUrl+"/"+invoicePK+"/anyponum")
                        .header("caller", TestUtil.caller)
                        .header("org", "Org1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveInvoiceFailNoCaller() throws Exception {
        doThrow(BadRequestException.class).when(mockInvoiceService).getInvoice(anyString(), anyString(), anyString(), anyString());

        this.mockMvc
                .perform(get(invoiceUrl+"/"+invoicePK+"/anyponum")
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRetrieveInvoice500() throws Exception {
        when(mockInvoiceService.getInvoice(any(), any(), any(), any())).thenThrow(InternalServerErrorException.class);


//        doThrow(InternalServerErrorException.class).when(mockInvoiceService).getInvoice(anyString(), anyString(), anyString(), anyString() );
        this.mockMvc
                .perform(get(invoiceUrl+"/"+invoicePK+"/anyponum")
                        .header("caller", "caller")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }


}
