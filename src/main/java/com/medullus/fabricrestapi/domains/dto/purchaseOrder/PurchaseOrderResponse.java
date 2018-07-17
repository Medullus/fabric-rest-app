package com.medullus.fabricrestapi.domains.dto.purchaseOrder;

import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class PurchaseOrderResponse {

    @ApiModelProperty
    private ResponseHeader responseHeader;

    @ApiModelProperty
    private List<PurchaseOrder> purchaseOrders;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrder(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    @Override
    public String toString() {
        return "PurchaseOrderResponse{" +
                "responseHeader=" + responseHeader +
                ", purchaseOrders=" + purchaseOrders +
                '}';
    }
}
