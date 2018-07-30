package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.dao.document.DocumentDao;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServiceBulkPojo;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.domains.dto.document.TXID;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DocumentServiceTest {

    @MockBean
    DocumentDao documentDao;

    @MockBean
    FabricClient fabricClient;

    @MockBean
    CompletableFuture<TXID> fut;

    @MockBean
    TXID id;

    @MockBean
    CompletableFuture<String> txFut;




    DocumentService documentService;
    DocumentServiceBulkPojo documentServiceBulkPojo;
    DocumentServicePojo documentServicePojo;

    @Before
    public void setup() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        documentService = new DocumentService(documentDao, fabricClient);

        documentServiceBulkPojo = new DocumentServiceBulkPojo();
        documentServiceBulkPojo.setOrg(TestUtil.org);
        documentServiceBulkPojo.setCaller(TestUtil.caller);
//        documentServiceBulkPojo.se
        List<Map<String, Object>> documents = new ArrayList<>();
        for(int i = 0; i< 5; i++){
            Map<String, Object> obj = new HashMap<>();
            obj.put("documentPK", new String("PKvalue"+i));
            obj.put("any", new String("value"+i));
            documents.add(obj);
        }
        documentServiceBulkPojo.setDocuments(documents);
        when(fabricClient.hasMember(any())).thenReturn(true);

        when(documentDao.addDocument(any(), any())).thenReturn(fut);
        when(fut.isDone()).thenReturn(true);
        when(fut.get()).thenReturn(id);

        when(id.getTxId()).thenReturn(txFut);
        when(txFut.isDone()).thenReturn(true);
        when(txFut.get()).thenReturn(TestUtil.txId);

    }

    @Test
    public void testAddBulk() throws ExecutionException, InterruptedException {
        Map<String, String> results = documentService.addDocument(documentServiceBulkPojo, "any", TestUtil.caller);

        assertEquals(5, results.size());
    }

    @Test
    public void testGetDoc() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        when(documentDao.getDocument(any(), any(), any())).thenReturn("anydoc");
        assertNotNull(documentService.getDocument("any", "any", "any"));
    }

    @Test(expected = InternalServerErrorException.class)
    public void testGetDocThrows() throws IOException, TransactionException, InstantiationException, InvocationTargetException, ExecutionException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, NoSuchMethodException, ClassNotFoundException {
        when(documentDao.getDocument(any(), any(), any())).thenThrow(ProposalException.class);
        documentService.getDocument("any", "any", "any");
    }
}
