package com.medullus.fabricrestapi.domains.dto.purchaseOrder;

import java.util.List;

public class PurchaseOrderServicePojo {
    private String caller;
    private String org;
    private List<PurchaseOrder> purchaseOrdersList;

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

    public List<PurchaseOrder> getPurchaseOrdersList() {
        return purchaseOrdersList;
    }

    public void setPurchaseOrdersList(List<PurchaseOrder> purchaseOrdersList) {
        this.purchaseOrdersList = purchaseOrdersList;
    }

    @Override
    public String toString() {
        return "InvoiceServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", purchaseOrders=" + purchaseOrdersList +
                '}';
    }
}
