package com.medullus.fabricrestapi.domains.dto.errorTransaction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ErrorTransaction {

    /**
     * refId : string
     * seller : string
     * buyer : string
     * poNum : alphanumeric
     * sku : int
     * quantity : int
     * currency : string
     * unit : int
     * amount : int
     * invStts : string
     * disputeReason : string
     * disputeResDate : date
     * disputeResSteps : string
     */

    @ApiModelProperty(example = "80203")
    private String refId;
    @ApiModelProperty(example = "A6")
    private String seller;
    @ApiModelProperty(example = "A2")
    private String buyer;
    @ApiModelProperty(example = "A9854")
    private String poNum;
    @ApiModelProperty(example = "23598")
    private String sku;
    @ApiModelProperty(example = "180")
    private String quantity;
    @ApiModelProperty(example = "USD")
    private String currency;
    @ApiModelProperty(example = "100")
    private String unit;
    @ApiModelProperty(example = "18,000")
    private String amount;
    @ApiModelProperty(example = "error")
    private String invStts;
    @ApiModelProperty(example = "PO Exceed NTE quantity")
    private String disputeReason;
    @ApiModelProperty(example = "25-Jan")
    private String disputeResDate;
    @ApiModelProperty(example = "Old PO amended for the quantity")
    private String disputeResSteps;

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

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInvStts() {
        return invStts;
    }

    public void setInvStts(String invStts) {
        this.invStts = invStts;
    }

    public String getDisputeReason() {
        return disputeReason;
    }

    public void setDisputeReason(String disputeReason) {
        this.disputeReason = disputeReason;
    }

    public String getDisputeResDate() {
        return disputeResDate;
    }

    public void setDisputeResDate(String disputeResDate) {
        this.disputeResDate = disputeResDate;
    }

    public String getDisputeResSteps() {
        return disputeResSteps;
    }

    public void setDisputeResSteps(String disputeResSteps) {
        this.disputeResSteps = disputeResSteps;
    }
}
