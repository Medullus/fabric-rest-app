package com.medullus.fabricrestapi.resources.apis.documents;

import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.document.DocumentRequest;
import com.medullus.fabricrestapi.domains.dto.document.DocumentResponse;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServiceBulkPojo;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.medullus.fabricrestapi.resources.apis.document.DocumentMapper;
import com.medullus.fabricrestapi.services.DocumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class DocumentMapperTests {

    @MockBean
    DocumentService mockDocumentService;

    DocumentMapper documentMapper;

    DocumentRequest documentRequest;

    @Before
    public void setup(){
        documentMapper = new DocumentMapper(mockDocumentService);

        DocumentRequest documentRequestMock = new DocumentRequest();
        documentRequestMock.setRequestHeader(TestUtil.getReqHeader());
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("key1", "value1");
        obj1.put("key2", "value2");
        Map<String, Object> obj2 = new HashMap<>();
        obj1.put("key1", "value1");
        obj1.put("key2", "value2");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        documentRequestMock.setDocuments(list);

        this.documentRequest = documentRequestMock;
    }

    @Test
    public void testMapResourceToServicePojo(){
        DocumentServiceBulkPojo documentServiceBulkPojo = new DocumentServiceBulkPojo();
        documentServiceBulkPojo.setCaller(TestUtil.caller);
        documentServiceBulkPojo.setOrg(TestUtil.org);
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("key1", "value1");
        obj1.put("key2", "value2");
        Map<String, Object> obj2 = new HashMap<>();
        obj1.put("key1", "value1");
        obj1.put("key2", "value2");
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        documentServiceBulkPojo.setDocuments(list);

        assertEquals(list, documentMapper.mapResourceToServicePojo(this.documentRequest).getDocuments());

    }

    @Test
    public void testMapServiceObjToResponse(){
        String documents = "{\"key1\":\"value1\"}";
        String message = "message";
        Map<String, String> txLists = new HashMap<>();
        txLists.put("pk1","1234txID");
        txLists.put("pk2", "321txID");

        DocumentResponse documentResponse = documentMapper.mapServiceObjToResponse(documents, message, txLists);

        assertEquals("value1", documentResponse.getDocument().get("key1"));

        assertEquals(message, documentResponse.getResponseHeader().getMessage());
        assertEquals("1234txID", documentResponse.getTxResults().get("pk1"));

    }

    @Test(expected = InternalServerErrorException.class)
    public void testMapServiceObjToResponseError(){
        String documents = "[{\"key1\":\"value1\"}]";
        String message = "message";
        Map<String, String> txLists = new HashMap<>();
        txLists.put("pk1","1234txID");
        txLists.put("pk2", "321txID");

        DocumentResponse documentResponse = documentMapper.mapServiceObjToResponse(documents, message, txLists);


    }
}
