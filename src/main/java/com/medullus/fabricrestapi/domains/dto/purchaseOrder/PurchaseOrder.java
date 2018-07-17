package com.medullus.fabricrestapi.domains.dto.purchaseOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PurchaseOrder {

    /**
     * refId : string
     * buyer : string
     * doc : string
     * seller : string
     * sku : string
     * quantity : int
     * currency : string
     * unitCost : int/float
     * amount : int/float
     * type : string
     */

    @ApiModelProperty(example = "A1235")
    private String refId;
    @ApiModelProperty(example = "A5")
    private String buyer;
    @ApiModelProperty(example = "PO")
    private String doc;
    @ApiModelProperty(example = "A3")
    private String seller;
    @ApiModelProperty(example = "85412")
    private String sku;
    @ApiModelProperty(example = "200")
    private String quantity;
    @ApiModelProperty(example = "EUR")
    private String currency;
    @ApiModelProperty(example = "200")
    private String unitCost;
    @ApiModelProperty(example = "40000")
    private String amount;
    @ApiModelProperty(example = "STD")
    private String type;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
