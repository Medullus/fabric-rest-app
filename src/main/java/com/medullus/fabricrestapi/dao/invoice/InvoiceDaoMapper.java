package com.medullus.fabricrestapi.dao.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.medullus.fabricrestapi.domains.dto.invoice.Invoice;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;


public class InvoiceDaoMapper {

    private static Logger logger = Logger.getLogger(InvoiceDaoMapper.class);

    //TODO
    /**
     *  Note this is in the direction to fabric
     * **/
    public static String mapInvoiceToArgs(List<Invoice> invoices){
        Gson gson = new Gson();
        StringJoiner sj = new StringJoiner(",", "[","]");
        for(Invoice invoice : invoices){
            sj.add(gson.toJson(invoice, Invoice.class));
        }
        logger.debug("mapInvoiceToArgs");
        logger.debug(sj.toString());

        return sj.toString();
    }

    //TODO
    /**
     *  Note this in the direction back to client
     * **/
    public static Invoice[] mapArgsToInvoices(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Invoice[] invoices;
        try {
            invoices = objectMapper.readValue(payload, Invoice[].class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }

        return invoices;
    }
}
