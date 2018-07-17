package com.medullus.fabricrestapi.services;

import com.medullus.fabricrestapi.dao.users.UsersDao;
import com.medullus.fabricrestapi.domains.dto.users.UserRegister;
import com.medullus.fabricrestapi.domains.dto.users.UserServicePojo;
import com.medullus.fabricrestapi.exceptions.InternalServerErrorException;
import org.apache.log4j.Logger;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

@Service
public class UserService {

    private final Logger logger = Logger.getLogger(UserService.class);

    private UsersDao usersDao;

    @Autowired
    public UserService(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    public UserRegister addUserToNetwork(UserServicePojo userServicePojo){
        //TODO in the future use userServicePojo.getCaller and run check for admin

        try {
            return usersDao.addUserToNetwork(userServicePojo);
        } catch (EnrollmentException|RegistrationException|MalformedURLException|InvalidArgumentException e) {
            e.printStackTrace();
            logger.error(e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
