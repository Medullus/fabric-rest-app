package com.medullus.fabricrestapi.domains.dto.document;

public class TXID {

    public TXID(String txId) {
        this.txId = txId;
    }

    private String txId;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }
}
