package com.medullus.fabricrestapi.resources.apis.entityMaster;

import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterRequest;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterResponse;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
import com.medullus.fabricrestapi.services.EntityMasterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class EntityMasterMapperTest {
    @MockBean
    EntityMasterService mockEntityMasterService;

    EntityMasterMapper entityMasterMapper;
    EntityMasterRequest entityMasterRequest;

    @Before
    public void setup(){
        entityMasterMapper = new EntityMasterMapper(mockEntityMasterService);
        EntityMaster[] entityMasters = TestUtil.getEntityMasters();
        entityMasterRequest = new EntityMasterRequest();
        entityMasterRequest.setEntityMasters(Arrays.asList(entityMasters));
        entityMasterRequest.setRequestHeader(TestUtil.getReqHeader());
    }

    @Test
    public void testMapResourceToServicePojo(){
        EntityMasterServicePojo entityMasterServicePojo = entityMasterMapper.mapResourceToServicePojo(entityMasterRequest);

        assertEquals(TestUtil.caller, entityMasterServicePojo.getCaller());
        assertEquals(5, entityMasterServicePojo.getEntityMasters().size());

        for(EntityMaster entityMaster : entityMasterServicePojo.getEntityMasters()){
            assertEquals("A1", entityMaster.getGroup());
        }

    }

    @Test
    public void testMapServiceObjToResponse(){
        EntityMasterResponse entityMasterResponse = entityMasterMapper.mapServiceObjToResponse(TestUtil.getEntityMasters(), "blank", TestUtil.txId);

        assertEquals("blank", entityMasterResponse.getResponseHeader().getMessage());
        assertEquals(TestUtil.txId, entityMasterResponse.getResponseHeader().getTxID());
        assertEquals(5, entityMasterResponse.getEntityMasters().size());
    }
}
