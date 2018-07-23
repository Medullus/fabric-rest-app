package com.medullus.fabricrestapi.domains.dto.document;

import java.util.List;
import java.util.Map;

public class DocumentServiceBulkPojo {

    private String caller;
    private String org;
    private List<Map<String, Object>> documents;

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

    public List<Map<String, Object>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Map<String, Object>> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "DocumentServiceBulkPojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", documents=" + documents +
                '}';
    }
}
