package com.medullus.fabricrestapi.domains.dto.invoice;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.util.List;

@ApiModel
public class InvoiceResponse {


    /**
     * status : ok
     * invoices : [{"buyer":"buyer","seller":"seller","cost":"cost","item":"item"}]
     */

    @ApiModelProperty
    private ResponseHeader responseHeader;

    @ApiModelProperty
    private List<Invoice> invoices;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "InvoiceResponse{" +
                "responseHeader=" + responseHeader +
                ", invoices=" + invoices +
                '}';
    }
}
