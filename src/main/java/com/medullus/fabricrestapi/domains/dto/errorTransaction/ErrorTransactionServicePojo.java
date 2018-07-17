package com.medullus.fabricrestapi.domains.dto.errorTransaction;

import java.util.List;

public class ErrorTransactionServicePojo {

    private String caller;
    private String org;
    private List<ErrorTransaction> errorTransactions;

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

    public List<ErrorTransaction> getErrorTransactions() {
        return errorTransactions;
    }

    public void setErrorTransactions(List<ErrorTransaction> errorTransactions) {
        this.errorTransactions = errorTransactions;
    }

    @Override
    public String toString() {
        return "ErrorTransactionServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", errorTransactions=" + errorTransactions +
                '}';
    }
}
