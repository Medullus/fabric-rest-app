package com.medullus.fabricrestapi.domains.dto.entityMaster;

import java.util.List;

public class EntityMasterServicePojo {

    private String caller;
    private String org;
    private List<EntityMaster> entityMasters;

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

    public List<EntityMaster> getEntityMasters() {
        return entityMasters;
    }

    public void setEntityMasters(List<EntityMaster> entityMasters) {
        this.entityMasters = entityMasters;
    }

    @Override
    public String toString() {
        return "EntityMasterServicePojo{" +
                "caller='" + caller + '\'' +
                ", org='" + org + '\'' +
                ", entityMasters=" + entityMasters +
                '}';
    }
}
