package com.medullus.fabricrestapi.resources.apis.entityMaster;

import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterRequest;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterResponse;
import com.medullus.fabricrestapi.domains.dto.entityMaster.EntityMasterServicePojo;
import com.medullus.fabricrestapi.services.EntityMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Entity Master")
@RestController
@RequestMapping(path = "/entitymasters")
public class EntityMasterController {
    private static final Logger logger = Logger.getLogger(EntityMasterController.class);

    private EntityMasterService entityMasterService;
    private EntityMasterMapper entityMasterMapper;

    @Autowired
    public EntityMasterController(EntityMasterService entityMasterService, EntityMasterMapper entityMasterMapper) {
        this.entityMasterService = entityMasterService;
        this.entityMasterMapper = entityMasterMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update EntityMaster to ledger")
    @ApiResponses({
            @ApiResponse(code = 200, message = "EntityMaster updated" ),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error" )
    })
    public EntityMasterResponse updateInvoice(@RequestBody EntityMasterRequest entityMasterRequest){
        EntityMasterServicePojo servicePojo = entityMasterMapper.mapResourceToServicePojo(entityMasterRequest);
        logger.debug("Update Invoice");

        return entityMasterMapper.mapServiceObjToResponse(null, null, entityMasterService.updateEntityMaster(servicePojo));
    }

    @RequestMapping(value = "/{entityKey}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get EntityMaster from ledger. Use caller's id as entityKey")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public EntityMasterResponse getEntityMaster(@PathVariable("entityKey") String entityKey,
                                             @RequestHeader(value = "caller") String caller) {
        logger.debug("retrieving :" + entityKey);
        return entityMasterMapper.mapServiceObjToResponse(entityMasterService.getEntityMaster(entityKey, caller, null), "Found " + entityKey, null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add EntityMaster to ledger. For now, please only add 1 EntityMaster per POST as each EntityMaster's PK is the caller's ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "EntityMaster added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public EntityMasterResponse addEntityMaster(@RequestBody EntityMasterRequest entityMasterRequest) {
        EntityMasterServicePojo entityMasterServicePojo = entityMasterMapper.mapResourceToServicePojo(entityMasterRequest);
        logger.debug("adding entityMaster");
        return entityMasterMapper.mapServiceObjToResponse(null, null, entityMasterService.addEntityMaster(entityMasterServicePojo));
    }
}
