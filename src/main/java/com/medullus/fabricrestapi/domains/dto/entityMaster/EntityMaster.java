package com.medullus.fabricrestapi.domains.dto.entityMaster;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class EntityMaster {

    /**
     * subName : string
     * glEntityCode : string
     * group : string
     * fnlCurr : string
     * country : string
     * paymasterEligible : string
     * paymaster : string
     * bank : string
     * account : int
     * nettingSettRules : string
     * additionalReview : string
     * wht : string
     */

    @ApiModelProperty(example = "Atlas Holding")
    private String subName;
    @ApiModelProperty(example = "A0")
    private String glEntityCode;
    @ApiModelProperty(example = "A1")
    private String group;
    @ApiModelProperty(example = "USD")
    private String fnlCurr;
    @ApiModelProperty(example = "US")
    private String country;
    @ApiModelProperty(example = "Y")
    private String paymasterEligible;
    @ApiModelProperty(example = "A0")
    private String paymaster;
    @ApiModelProperty(example = "BOFAUS33")
    private String bank;
    @ApiModelProperty(example = "659871236")
    private String account;
    @ApiModelProperty(example = "No restrict")
    private String nettingSettRules;
    @ApiModelProperty(example = "N")
    private String additionalReview;
    @ApiModelProperty(example = "N")
    private String wht;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getGlEntityCode() {
        return glEntityCode;
    }

    public void setGlEntityCode(String glEntityCode) {
        this.glEntityCode = glEntityCode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFnlCurr() {
        return fnlCurr;
    }

    public void setFnlCurr(String fnlCurr) {
        this.fnlCurr = fnlCurr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPaymasterEligible() {
        return paymasterEligible;
    }

    public void setPaymasterEligible(String paymasterEligible) {
        this.paymasterEligible = paymasterEligible;
    }

    public String getPaymaster() {
        return paymaster;
    }

    public void setPaymaster(String paymaster) {
        this.paymaster = paymaster;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNettingSettRules() {
        return nettingSettRules;
    }

    public void setNettingSettRules(String nettingSettRules) {
        this.nettingSettRules = nettingSettRules;
    }

    public String getAdditionalReview() {
        return additionalReview;
    }

    public void setAdditionalReview(String additionalReview) {
        this.additionalReview = additionalReview;
    }

    public String getWht() {
        return wht;
    }

    public void setWht(String wht) {
        this.wht = wht;
    }
}
