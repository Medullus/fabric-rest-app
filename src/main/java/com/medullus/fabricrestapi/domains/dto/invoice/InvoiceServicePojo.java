package com.medullus.fabricrestapi.domains.dto.invoice;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/** Notice that this POJO doesn't require swagger doc annotation.
 * This DTO is internal and is not exposed to the client.
 *
 * **/
public class InvoiceServicePojo {

    private String caller;
    private String org;
    private List<Invoice> invoices;

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "InvoiceServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", invoices=" + invoices +
                '}';
    }
}
