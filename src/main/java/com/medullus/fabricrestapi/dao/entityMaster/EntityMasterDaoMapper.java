package com.medullus.fabricrestapi.dao.entityMaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.medullus.fabricrestapi.dao.invoice.InvoiceDaoMapper;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class EntityMasterDaoMapper {
    private static Logger logger = Logger.getLogger(EntityMasterDaoMapper.class);

    public static String mapEntityMasterToArgs(List<EntityMaster> entityMasters){
        Gson gson = new Gson();
        StringJoiner sj = new StringJoiner(",", "[","]");
        for(EntityMaster entityMaster: entityMasters){
            sj.add(gson.toJson(entityMaster, EntityMaster.class));
        }
        logger.debug("mapEntityMasterToArgs");
        logger.debug(sj.toString());

        return sj.toString();
    }

    public static EntityMaster[] mapArgsToEntityMaster(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        EntityMaster[] entityMasters;
        try {
            entityMasters = objectMapper.readValue(payload, EntityMaster[].class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }

        return entityMasters;
    }
}
