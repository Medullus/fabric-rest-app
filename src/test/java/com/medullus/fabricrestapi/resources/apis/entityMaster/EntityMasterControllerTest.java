package com.medullus.fabricrestapi.resources.apis.entityMaster;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterRequest;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterResponse;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
import com.medullus.fabricrestapi.exceptions.BadRequestException;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.services.EntityMasterService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EntityMasterController.class)
public class EntityMasterControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EntityMasterService mockEntityMasterService;

    @MockBean
    EntityMasterMapper mockEntityMasterMapper;

    EntityMasterRequest entityMasterRequest;
    EntityMasterResponse entityMasterResponse;
    EntityMaster[] entityMasters;

    final String entityMasterUrl = "/entitymasters";
    String jsonString;
    final String badJson = "badString";
    final String entityKey = "entityKey123";

    @Before
    public void setup() throws JsonProcessingException {

        entityMasters = TestUtil.getEntityMasters();

        this.entityMasterRequest = new EntityMasterRequest();
        this.entityMasterResponse = new EntityMasterResponse();

        entityMasterRequest.setRequestHeader(TestUtil.getReqHeader());
        entityMasterRequest.setEntityMasters(Arrays.asList(entityMasters));

        entityMasterResponse.setResponseHeader(TestUtil.getResponseHeader());
        entityMasterResponse.setEntityMasters(Arrays.asList(entityMasters));

        doCallRealMethod().when(mockEntityMasterMapper).mapServiceObjToResponse(TestUtil.getEntityMasters(), "blank", TestUtil.txId);
        doCallRealMethod().when(mockEntityMasterMapper).mapResourceToServicePojo(entityMasterRequest);

        doReturn(TestUtil.txId).when(mockEntityMasterService).addEntityMaster(any(EntityMasterServicePojo.class));
        doReturn(TestUtil.txId).when(mockEntityMasterService).updateEntityMaster(any(EntityMasterServicePojo.class));
        doReturn(entityMasters).when(mockEntityMasterService).getEntityMaster(anyString(), anyString(), anyString());

        this.jsonString = new ObjectMapper().writeValueAsString(entityMasterRequest);

    }

    /**
     * Test Add EntityMaster
     **/

    @Test
    public void testAddEntityMasterOK() throws Exception {
        this.mockMvc
                .perform(post(entityMasterUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEntityMasterFail() throws Exception {
        this.mockMvc
                .perform(post(entityMasterUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(badJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddInvoiceFail500() throws Exception {
        //THIS IS IMPORTANT!! Syntax! TODO different from when clause in setup
        doThrow(InternalServerErrorException.class).when(mockEntityMasterService).addEntityMaster(any());

        this.mockMvc
                .perform(post(entityMasterUrl).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    /**
     * Test Update EntityMaster
     **/

    @Test
    public void testUpdateEntityMasterOk() throws Exception {

        this.mockMvc
                .perform(put(entityMasterUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFail() throws Exception {
        this.mockMvc
                .perform(put(entityMasterUrl).accept(MediaType.APPLICATION_JSON).content(badJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate500Error() throws Exception {
        doThrow(InternalServerErrorException.class).when(mockEntityMasterService).updateEntityMaster(any());

        this.mockMvc
                .perform(put(entityMasterUrl).accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test Retrieve Entity Master
     **/

    @Test
    public void testRetrieveEntityMaster() throws Exception {
        this.mockMvc
                .perform(get(entityMasterUrl + "/" + entityKey)
                        .header("caller", TestUtil.caller)
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveEntityMasterFail() throws Exception {
        doThrow(BadRequestException.class).when(mockEntityMasterService).getEntityMaster(anyString(), anyString(), anyString());

        this.mockMvc
                .perform(get(entityMasterUrl + "/" + entityKey)
                        .header("caller", "")
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRetrieveEntityMaster500() throws Exception {
        doThrow(InternalServerErrorException.class).when(mockEntityMasterService).getEntityMaster(anyString(), anyString(), anyString());
        this.mockMvc
                .perform(get(entityMasterUrl + "/" + entityKey)
                        .header("caller", "caller")
                        .header("org", "Org1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

}
