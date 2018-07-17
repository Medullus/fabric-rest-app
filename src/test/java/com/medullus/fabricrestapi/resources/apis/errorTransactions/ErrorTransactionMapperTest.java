package com.medullus.fabricrestapi.resources.apis.errorTransactions;

import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionRequest;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionResponse;
import com.medullus.fabricrestapi.resources.apis.errorTransaction.ErrorTransactionMapper;
import com.medullus.fabricrestapi.services.ErrorTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ErrorTransactionMapperTest {

    @MockBean
    ErrorTransactionService mockErrorTransactionService;

    ErrorTransactionMapper errorTransactionMapper;
    ErrorTransactionRequest errorTransactionRequest;

    @Before
    public void setup(){
        errorTransactionMapper = new ErrorTransactionMapper(mockErrorTransactionService);
        ErrorTransaction[] errorTransactions = TestUtil.getErrorTransactions();
        errorTransactionRequest = new ErrorTransactionRequest();
        errorTransactionRequest.setErrorTransactions(Arrays.asList(errorTransactions));
        errorTransactionRequest.setRequestHeader(TestUtil.getReqHeader());
    }

    @Test
    public void testMapServiceObjToResponse(){
        ErrorTransactionResponse errorTransactionResponse = errorTransactionMapper.mapServiceObjToResponse(TestUtil.getErrorTransactions(), "blank", TestUtil.txId);

        assertEquals("blank", errorTransactionResponse.getResponseHeader().getMessage());
        assertEquals(TestUtil.txId, errorTransactionResponse.getResponseHeader().getTxID());
        assertEquals(5, errorTransactionResponse.getErrorTransactions().size());
    }
}
