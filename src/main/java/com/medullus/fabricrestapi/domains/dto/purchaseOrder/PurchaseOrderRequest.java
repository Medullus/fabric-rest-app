package com.medullus.fabricrestapi.domains.dto.purchaseOrder;

import com.medullus.fabricrestapi.domains.dto.RequestHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class PurchaseOrderRequest {

    @ApiModelProperty
    private RequestHeader requestHeader;

    @ApiModelProperty
    private List<PurchaseOrder> purchaseOrderList;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    @Override
    public String toString() {
        return "PurchaseOrderRequest{" +
                "requestHeader=" + requestHeader +
                ", purchaseOrderList=" + purchaseOrderList +
                '}';
    }
}
