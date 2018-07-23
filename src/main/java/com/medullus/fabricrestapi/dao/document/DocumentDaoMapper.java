package com.medullus.fabricrestapi.dao.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.medullus.fabricrestapi.dao.invoice.InvoiceDaoMapper;
import com.medullus.fabricrestapi.domains.dto.document.Document;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;



public class DocumentDaoMapper {
    private static Logger logger = Logger.getLogger(DocumentDaoMapper.class);

    public static String mapDocumentToArgs(Map<String,Object> documents){
        Gson objGson = new Gson();
        String anyJsonStr = objGson.toJson(documents);

        logger.debug("mapped json to string for args:"+anyJsonStr);
        return anyJsonStr;
    }

}
