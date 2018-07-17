package com.medullus.fabricrestapi.resources.apis.purchaseOrder;

import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderRequest;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderResponse;
import com.medullus.fabricrestapi.domains.dto.purchaseOrder.PurchaseOrderServicePojo;
import com.medullus.fabricrestapi.services.PurchaseOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Purchase Order")
@RestController
@RequestMapping(path = "/purchaseorders")
public class PurchaseOrderController {
    private static final Logger logger = Logger.getLogger(PurchaseOrderController.class);

    private PurchaseOrderService purchaseOrderService;
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update Purchase Order to ledger")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Purchase Order updated"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PurchaseOrderResponse updatePurchaseOrder(@RequestBody PurchaseOrderRequest purchaseOrderRequest) {
        PurchaseOrderServicePojo servicePojo = purchaseOrderMapper.mapResourceToServicePojo(purchaseOrderRequest);
        logger.debug("Update Invoice");

        return purchaseOrderMapper.mapServiceObjToResponse(null, null, purchaseOrderService.updatePurchaseOrder(servicePojo));
    }

    @RequestMapping(value = "/{poNumber}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get Purchase Order from ledger. Use the refId from the PurchaseOrder Object")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PurchaseOrderResponse getPurchaseOrder(@PathVariable("poNumber") String poNumber,
                                                  @RequestHeader(value = "caller") String caller) {
        logger.debug("retrieving :" + poNumber);
        return purchaseOrderMapper.mapServiceObjToResponse(purchaseOrderService.getPurchaseOrder(poNumber, caller, null), "Found " + poNumber, null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add Purchase Order to ledger. 'caller' field must be a registered use from /users api. refId is poNumber and will be used in chaincodelayer to create composite key using caller's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public PurchaseOrderResponse addPurchaseOrder(@RequestBody PurchaseOrderRequest purchaseOrderRequest) {

        PurchaseOrderServicePojo purchaseOrderServicePojo = purchaseOrderMapper.mapResourceToServicePojo(purchaseOrderRequest);
        logger.debug("adding po");
        return purchaseOrderMapper.mapServiceObjToResponse(null, null, purchaseOrderService.addPurchaseOrder(purchaseOrderServicePojo));
    }
}
