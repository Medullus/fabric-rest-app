package com.medullus.fabricrestapi.domains.dto.document;

import java.util.List;
import java.util.Map;

public class DocumentServicePojo {

    private String caller;
    private String org;

    public Map<String, Object> getDocument() {
        return document;
    }

    public void setDocument(Map<String, Object> document) {
        this.document = document;
    }

    private Map<String, Object> document;

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



    @Override
    public String toString() {
        return "EntityMasterServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", documents=" + document +
                '}';
    }
}
