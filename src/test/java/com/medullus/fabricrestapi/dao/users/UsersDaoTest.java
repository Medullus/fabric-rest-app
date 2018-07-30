package com.medullus.fabricrestapi.dao.users;

import com.medullus.fabricrestapi.domains.dto.users.UserRegister;
import com.medullus.fabricrestapi.domains.dto.users.UserServicePojo;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.models.SampleStore;
import com.mhc.fabric.client.models.SampleUser;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.RegistrationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsersDaoTest {

    @MockBean
    private FabricClient fabricClient;

    @MockBean
    private SampleStore sampleStore;

    private UsersDao usersDao;
    private UserServicePojo userServicePojo;

    @Before
    public void setup() throws InvalidArgumentException, MalformedURLException, RegistrationException, EnrollmentException {
        this.usersDao = new UsersDao(fabricClient);
        SampleUser sampleUser = new SampleUser("name", "org", sampleStore);
        sampleUser.setEnrollmentSecret("pw");
        when(fabricClient.addMemberToNetwork(any(), any())).thenReturn(sampleUser);

        this.userServicePojo = new UserServicePojo();
        UserRegister userRegister = new UserRegister();
        userRegister.setSecret("pw");
        userRegister.setUserName("name");
        userServicePojo.setUserRegister(userRegister);


    }

    @Test
    public void testAdduser() throws InvalidArgumentException, MalformedURLException, RegistrationException, EnrollmentException {
        UserRegister userRegister = usersDao.addUserToNetwork(userServicePojo);

        assertEquals("name", userRegister.getUserName());
        assertEquals("pw", userRegister.getSecret());

    }

    @Test(expected = RegistrationException.class)
    public void testAddException() throws InvalidArgumentException, MalformedURLException, RegistrationException, EnrollmentException {
        when(fabricClient.addMemberToNetwork(any(), any())).thenThrow(RegistrationException.class);

        usersDao.addUserToNetwork(userServicePojo);
    }
}
