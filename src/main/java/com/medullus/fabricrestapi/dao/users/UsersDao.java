package com.medullus.fabricrestapi.dao.users;

import com.medullus.fabricrestapi.domains.dto.users.UserRegister;
import com.medullus.fabricrestapi.domains.dto.users.UserServicePojo;
import com.mhc.fabric.client.FabricClient;
import com.mhc.fabric.client.models.SampleUser;
import org.apache.log4j.Logger;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class UsersDao {
    private final Logger logger = Logger.getLogger(UsersDao.class);

    private FabricClient fabricClient;

    @Autowired
    public UsersDao(FabricClient fabricClient){
        this.fabricClient = fabricClient;
    }

    public UserRegister addUserToNetwork(UserServicePojo userServicePojo) throws InvalidArgumentException, MalformedURLException, RegistrationException, EnrollmentException {
        SampleUser sampleUser;
        UserRegister userRegister = userServicePojo.getUserRegister();

        try {
            sampleUser = fabricClient.addMemberToNetwork(userRegister.getUserName(), userRegister.getSecret());
        } catch (EnrollmentException|RegistrationException|MalformedURLException|InvalidArgumentException e) {
            e.printStackTrace();
            logger.error(e);
            throw e;
        }
        return UsersDaoMapper.mapSampleUserToUserRegister(sampleUser);
    }
}
