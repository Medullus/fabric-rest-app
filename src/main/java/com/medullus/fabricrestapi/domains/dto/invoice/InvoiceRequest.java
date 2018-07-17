package com.medullus.fabricrestapi.domains.dto.invoice;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class InvoiceRequest {


    @ApiModelProperty
    private RequestHeader requestHeader;

    @ApiModelProperty
    private List<Invoice> invoices;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "requestHeader=" + requestHeader +
                ", invoices=" + invoices +
                '}';
    }
}
