package com.medullus.fabricrestapi.dao.document;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.domains.dto.document.TXID;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.config.FabricConfig;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DocumentDaoTest {

    @MockBean
    private FabricClient mockFabricClient;

    @MockBean
    private FabricConfig mockFabricConfig;

    @MockBean
    private NetworkConfig mockNetworkConfig;

    private DocumentDao documentDao;
    private DocumentServicePojo documentServicePojo;

    @Before
    public void setup() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {

        this.documentDao = new DocumentDao(mockFabricClient, mockFabricConfig);

        when(mockFabricClient.getNetworkConfig()).thenReturn(mockNetworkConfig);
        Set<String> mockSetString = mock(Set.class);
        Iterator mockIterator = mock(Iterator.class);
        when(mockNetworkConfig.getChannelNames()).thenReturn(mockSetString);
        when(mockSetString.iterator()).thenReturn(mockIterator);
        when(mockIterator.next()).thenReturn(TestUtil.channel);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCNAME)).thenReturn(TestUtil.ccName);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCVER)).thenReturn(TestUtil.ccVer);
        when(mockFabricClient.invoke(any(),any(), any(), any())).thenReturn(TestUtil.txId);

        this.documentServicePojo = new DocumentServicePojo();
        documentServicePojo.setCaller(TestUtil.caller);
        documentServicePojo.setOrg(TestUtil.org);
        Map<String, Object> map = new HashMap<>();
        map.put("pk1", new String("value1"));
        map.put("pk2", new String("value2"));
        documentServicePojo.setDocument(map);
    }

    @Test
    public void testGetDoc() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        when(mockFabricClient.query(any(), any(), any(), any())).thenReturn("anything");
        assertEquals("anything", documentDao.getDocument("any", "any", "any"));
    }

    @Test(expected = ProposalException.class)
    public void testGetDocThrows() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        when(mockFabricClient.query(any(), any(), any(), any())).thenThrow(ProposalException.class);
        documentDao.getDocument("any", "any", "any");
    }

    @Test
    public void testAddDoc() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        CompletableFuture<TXID> fut = documentDao.addDocument(documentServicePojo, "pk1");
        assertEquals(TestUtil.txId, fut.get().getTxId());
    }
}
