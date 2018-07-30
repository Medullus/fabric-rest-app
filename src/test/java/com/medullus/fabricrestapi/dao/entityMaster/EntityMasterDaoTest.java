package com.medullus.fabricrestapi.dao.entityMaster;


import com.medullus.fabricrestapi.TestUtil;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMaster;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCNAME;
import static com.mhc.fabric.client.config.FabricConfigParams.MHC_FABRIC_CCVER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EntityMasterDaoTest {

    @MockBean
    private FabricClient mockFabricClient;

    @MockBean
    private FabricConfig mockFabricConfig;

    @MockBean
    private NetworkConfig mockNetworkConfig;

    private EntityMasterDao entityMasterDao;
    private EntityMasterServicePojo entityMasterServicePojo;

    @Before
    public void setup() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        this.entityMasterDao = new EntityMasterDao(mockFabricClient, mockFabricConfig);
        when(mockFabricClient.getNetworkConfig()).thenReturn(mockNetworkConfig);
        Set<String> mockSetString = mock(Set.class);
        Iterator mockIterator = mock(Iterator.class);
        when(mockNetworkConfig.getChannelNames()).thenReturn(mockSetString);
        when(mockSetString.iterator()).thenReturn(mockIterator);
        when(mockIterator.next()).thenReturn(TestUtil.channel);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCNAME)).thenReturn(TestUtil.ccName);
        when(mockFabricConfig.getProperty(MHC_FABRIC_CCVER)).thenReturn(TestUtil.ccVer);
        when(mockFabricClient.invoke(any(),any(), any(), any())).thenReturn(TestUtil.getFut());

        this.entityMasterServicePojo = new EntityMasterServicePojo();
        entityMasterServicePojo.setCaller(TestUtil.caller);
        entityMasterServicePojo.setOrg(TestUtil.org);
        entityMasterServicePojo.setEntityMasters(Arrays.asList(TestUtil.getEntityMasters()));
    }

    @Test
    public void testAddAndUpdate() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, CryptoException, NetworkConfigurationException, ClassNotFoundException, TransactionException, ProposalException {
        assertNotNull(this.entityMasterDao.addEntityMaster(this.entityMasterServicePojo));
        assertNotNull(this.entityMasterDao.updateEntityMaster(this.entityMasterServicePojo));
    }

    @Test(expected = ProposalException.class)
    public void testInvokeHelperThrow() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, ProposalException, NetworkConfigurationException, CryptoException, TransactionException, ClassNotFoundException {
        when(mockFabricClient.invoke(any(), any(), any(), any())).thenThrow(ProposalException.class);

        entityMasterDao.addEntityMaster(entityMasterServicePojo);
    }

    @Test
    public void testGetEntityMas() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        String entities = "[{\n" +
                "      \"account\": 659871236,\n" +
                "      \"additionalReview\": \"N\",\n" +
                "      \"bank\": \"BOFAUS33\",\n" +
                "      \"country\": \"US\",\n" +
                "      \"fnlCurr\": \"USD\",\n" +
                "      \"glEntityCode\": \"A0\",\n" +
                "      \"group\": \"A1\",\n" +
                "      \"nettingSettRules\": \"No restrict\",\n" +
                "      \"paymaster\": \"A0\",\n" +
                "      \"paymasterEligible\": \"Y\",\n" +
                "      \"subName\": \"Atlas Holding\",\n" +
                "      \"wht\": \"N\"\n" +
                "    }]";
        when(mockFabricClient.query(any(), any(), any(), any())).thenReturn(entities);

        EntityMaster[] entityMasters = entityMasterDao.getEntityMaster("any", "any", "any");

        assertEquals(1, entityMasters.length);
        assertEquals("BOFAUS33", entityMasters[0].getBank());
    }

    @Test(expected = ProposalException.class)
    public void testGetThrows() throws ExecutionException, InstantiationException, InvocationTargetException, NoSuchMethodException, InterruptedException, IllegalAccessException, InvalidArgumentException, NetworkConfigurationException, CryptoException, ClassNotFoundException, TransactionException, ProposalException, IOException {
        when(mockFabricClient.query(any(), any(), any(), any())).thenThrow(ProposalException.class);

        entityMasterDao.getEntityMaster("any", "any", "any");
    }
}
