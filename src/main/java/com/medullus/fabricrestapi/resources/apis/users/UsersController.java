package com.medullus.fabricrestapi.resources.apis.users;

import com.medullus.fabricrestapi.domains.dto.users.UserRegisterRequest;
import com.medullus.fabricrestapi.domains.dto.users.UserRegisterResponse;
import com.medullus.fabricrestapi.domains.dto.users.UserServicePojo;
import com.medullus.fabricrestapi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User")
@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    private final Logger logger = Logger.getLogger(UsersController.class);

    private UserService userService;
    private UsersMapper usersMapper;

    @Autowired
    public UsersController(UserService userService, UsersMapper usersMapper){
        this.usersMapper = usersMapper;
        this.userService = userService;
    }

    @RequestMapping( value = "", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Add a user to the network. Ignore requestHeader, this field will be used later for org's admin to register their member")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public UserRegisterResponse addToNetwork(@RequestBody UserRegisterRequest userRegisterRequest){
        logger.debug("registering user: "+userRegisterRequest.getUserRegister().getUserName());

        UserServicePojo userServicePojo = usersMapper.mapReqToService(userRegisterRequest);

        return usersMapper.mapServiceToResponse(userService.addUserToNetwork(userServicePojo), "user added");
    }

}
