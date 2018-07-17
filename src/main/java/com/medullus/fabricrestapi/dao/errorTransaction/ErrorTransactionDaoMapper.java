package com.medullus.fabricrestapi.dao.errorTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.dao.invoice.InvoiceDaoMapper;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ErrorTransactionDaoMapper {
    private static Logger logger = Logger.getLogger(InvoiceDaoMapper.class);

    public static ErrorTransaction[] mapArgsToErrorTransaction(String payload) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ErrorTransaction[] errorTransactions;
        try {
            errorTransactions = objectMapper.readValue(payload, ErrorTransaction[].class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }

        return errorTransactions;
    }
}
