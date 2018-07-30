package com.medullus.fabricrestapi.resources.apis.errorTransactions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransaction;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionRequest;
import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionResponse;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.resources.apis.errorTransaction.ErrorTransactionController;
import com.medullus.fabricrestapi.resources.apis.errorTransaction.ErrorTransactionMapper;
import com.medullus.fabricrestapi.services.ErrorTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ErrorTransactionController.class)
public class ErrorTransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ErrorTransactionService mockErrorTransactionService;

    @MockBean
    ErrorTransactionMapper mockErrorTransactionMapper;

    final String errorTransactionUrl = "/mismatches";

    ErrorTransactionRequest errorTransactionRequest;
    ErrorTransactionResponse errorTransactionResponse;
    ErrorTransaction[] errorTransactions;
    String jsonString;

    @Before
    public void setup() {


        errorTransactions = TestUtil.getErrorTransactions();

        this.errorTransactionRequest = new ErrorTransactionRequest();
        this.errorTransactionResponse= new ErrorTransactionResponse();

        errorTransactionRequest.setRequestHeader(TestUtil.getReqHeader());
        errorTransactionRequest.setErrorTransactions(Arrays.asList(errorTransactions));

        errorTransactionResponse.setResponseHeader(TestUtil.getResponseHeader());
        errorTransactionResponse.setErrorTransactions(Arrays.asList(errorTransactions));

    }

    /**
     * Test Retrieve Error Transaction
     **/

    @Test
    public void testRetrieveErrorTransaction() throws Exception {
        this.mockMvc
                .perform(get(errorTransactionUrl)
                        .header("caller", TestUtil.caller)
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveErrorTransactionFailNoCaller() throws Exception {

        this.mockMvc
                .perform(get(errorTransactionUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRetrieveErrorTransaction500() throws Exception {

        when(mockErrorTransactionService.getErrorTransaction(any(), any())).thenThrow(InternalServerErrorException.class);
        this.mockMvc
                .perform(get(errorTransactionUrl)
                        .header("caller", "caller")
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void testDelete() throws Exception {

        this.mockMvc
                .perform(delete("/mismatches/delete/callerA")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
