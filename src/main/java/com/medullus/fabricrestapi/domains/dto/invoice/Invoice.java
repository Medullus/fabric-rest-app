package com.medullus.fabricrestapi.domains.dto.invoice;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Invoice {


    /**
     * refId : string
     * seller :
     * date :
     * buyer :
     * poNumber :
     * sku :
     * quantity : int
     * currency :
     * unitCost : int/float
     * amount : int/float
     */

    @ApiModelProperty(example = "68109")
    private String refId;
    @ApiModelProperty(example = "A3")
    private String seller;
    @ApiModelProperty(example = "2-Jan")
    private String date;
    @ApiModelProperty(example = "A5")
    private String buyer;
    @ApiModelProperty(example = "A1235")
    private String poNumber;
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

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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
}
