package com.medullus.fabricrestapi.domains.dto.document;

import java.util.List;
import java.util.Map;

public class DocumentServicePojo {

    private String caller;
    private String org;
    private Map<String, Object> documents;

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

    public Map<String, Object> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, Object> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "EntityMasterServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", documents=" + documents +
                '}';
    }
}
