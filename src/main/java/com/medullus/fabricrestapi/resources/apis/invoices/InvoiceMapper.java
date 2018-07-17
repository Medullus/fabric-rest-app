package com.medullus.fabricrestapi.resources.apis.invoices;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceResponse;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.services.InvoiceService;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InvoiceMapper {

    //TODO
    /**
     *  Note this is in direction to Fabric
     * **/

    private InvoiceService invoiceService;
    private Mapper mapper;

    @Autowired
    public InvoiceMapper(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
        mapper = DozerBeanMapperBuilder
                .create()
                .withMappingBuilder(getBuilder())
                .build();
    }


    public InvoiceServicePojo mapResourceToServicePojo(InvoiceRequest invoiceRequest){
        InvoiceServicePojo validInvoiceStub = mapper.map(invoiceRequest, InvoiceServicePojo.class);

        return validInvoiceStub;
    }

    //TODO
    /**
     * Note this is in direction back to client
     * **/
    public InvoiceResponse mapServiceObjToResponse(Invoice[] invoices, String message, String txId){
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        if(invoices != null){
            invoiceResponse.setInvoices(Arrays.asList(invoices));
        }
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setMessage(message);
        responseHeader.setTxID(txId);
        invoiceResponse.setResponseHeader(responseHeader);

        return invoiceResponse;
    }


    private BeanMappingBuilder getBuilder(){
        return new BeanMappingBuilder() {
            final String invoices = "invoices";
            @Override
            protected void configure() {
                mapping(InvoiceRequest.class, InvoiceServicePojo.class)
                        .fields("requestHeader.caller", "caller")
                        .fields("requestHeader.org", "org")
                        .fields(invoices, invoices);
            }
        };
    }


}
