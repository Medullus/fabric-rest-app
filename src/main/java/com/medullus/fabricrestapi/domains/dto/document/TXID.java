package com.medullus.fabricrestapi.domains.dto.document;

import java.util.concurrent.CompletableFuture;

public class TXID {

    public CompletableFuture<String> getTxId() {
        return txId;
    }

    public void setTxId(CompletableFuture<String> txId) {
        this.txId = txId;
    }

    public TXID(CompletableFuture<String> txId) {

        this.txId = txId;
    }

    private CompletableFuture<String> txId;
}
